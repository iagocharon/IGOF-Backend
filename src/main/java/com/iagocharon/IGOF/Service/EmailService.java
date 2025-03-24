package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class EmailService {

  private final JavaMailSender mailSender;
  private final TaskScheduler taskScheduler;
  private final HashMap<UUID, ScheduledFuture<?>> scheduledReminders =
    new HashMap<>();

  @Autowired
  public EmailService(JavaMailSender mailSender, TaskScheduler taskScheduler) {
    this.mailSender = mailSender;
    this.taskScheduler = taskScheduler;
  }

  public void sendNewMail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@igof.com.ar");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
  }

  public void scheduleReminder(Appointment appointment) {
    ZonedDateTime reminderTime = appointment.getStart().minusHours(24);
    Instant instant = reminderTime.toInstant();

    ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
      () -> {
        sendNewMail(
          appointment.getPatient().getEmail(),
          "Recordatorio de turno",
          "Hola " +
          appointment.getPatient().getName() +
          ", te recordamos que tienes un turno con el Dr. " +
          appointment.getDoctor().getLastname() +
          ", " +
          appointment.getDoctor().getName() +
          " el " +
          appointment.getStart().toLocalDate() +
          " a las " +
          appointment.getStart().toLocalTime()
        );
      },
      instant
    );

    scheduledReminders.put(appointment.getId(), scheduledTask);
  }

  public void scheduleUltrasoundReminder(UltrasoundAppointment appointment) {
    ZonedDateTime reminderTime = appointment.getStart().minusHours(24);
    Instant instant = reminderTime.toInstant();

    ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
      () -> {
        sendNewMail(
          appointment.getPatient().getEmail(),
          "Recordatorio de turno",
          "Hola " +
          appointment.getPatient().getName() +
          ", te recordamos que tienes un turno con el Dr. " +
          appointment.getUltrasoundDoctor().getLastname() +
          ", " +
          appointment.getUltrasoundDoctor().getName() +
          " el " +
          appointment.getStart().toLocalDate() +
          " a las " +
          appointment.getStart().toLocalTime()
        );
      },
      instant
    );

    scheduledReminders.put(appointment.getId(), scheduledTask);
  }

  public void cancelReminder(UUID appointmentId) {
    ScheduledFuture<?> scheduledTask = scheduledReminders.remove(appointmentId);
    if (scheduledTask != null) {
      scheduledTask.cancel(false);
    }
  }
}
