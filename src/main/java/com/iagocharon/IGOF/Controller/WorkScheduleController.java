package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.AppointmentProjection;
import com.iagocharon.IGOF.Dto.Projections.UltrasoundAppointmentProjection;
import com.iagocharon.IGOF.Dto.WorkScheduleDto;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.AppointmentStatus;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Service.AppointmentService;
import com.iagocharon.IGOF.Service.DoctorService;
import com.iagocharon.IGOF.Service.EmailService;
import com.iagocharon.IGOF.Service.UltrasoundAppointmentService;
import com.iagocharon.IGOF.Service.UltrasoundDoctorService;
import com.iagocharon.IGOF.Service.WorkScheduleService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workschedule")
public class WorkScheduleController {

  @Autowired
  WorkScheduleService workScheduleService;

  @Autowired
  DoctorService doctorService;

  @Autowired
  UltrasoundDoctorService ultrasoundDoctorService;

  @Autowired
  AppointmentService appointmentService;

  @Autowired
  UltrasoundAppointmentService ultrasoundAppointmentService;

  @Autowired
  EmailService emailService;

  @GetMapping("/list")
  public ResponseEntity<?> getWorkSchedulesBetweenDates(
    @RequestParam String doctorId,
    @RequestParam String from,
    @RequestParam String to
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalDate startDate = LocalDate.parse(from, formatter);
    LocalDate endDate = LocalDate.parse(to, formatter);

    if (!doctorService.existsById(UUID.fromString(doctorId))) {
      if (!ultrasoundDoctorService.existsById(UUID.fromString(doctorId))) {
        return new ResponseEntity<>(
          new Message("Doctor not found."),
          HttpStatus.NOT_FOUND
        );
      }
      List<WorkSchedule> schedules =
        workScheduleService.findUltrasoundDoctorBetweenDates(
          UUID.fromString(doctorId),
          startDate,
          endDate
        );
      return new ResponseEntity<>(schedules, HttpStatus.OK);
    } else {
      List<WorkSchedule> schedules = workScheduleService.findDoctorBetweenDates(
        UUID.fromString(doctorId),
        startDate,
        endDate
      );
      return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<?> addWorkSchedule(
    @RequestBody WorkScheduleDto workScheduleDto
  ) {
    UUID doctorId = UUID.fromString(workScheduleDto.getDoctorId());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalTime start = LocalTime.parse(workScheduleDto.getStart(), formatter);
    LocalTime end = LocalTime.parse(workScheduleDto.getEnd(), formatter);
    LocalDate date = LocalDate.parse(workScheduleDto.getDate(), formatter);

    if (!doctorService.existsById(doctorId)) {
      if (!ultrasoundDoctorService.existsById(doctorId)) {
        return new ResponseEntity<>(
          new Message("Doctor not found."),
          HttpStatus.NOT_FOUND
        );
      }
      List<WorkSchedule> schedules =
        workScheduleService.findByUltrasoundDoctorIdAndDate(doctorId, date);

      for (WorkSchedule schedule : schedules) {
        System.out.println("Schedule: ");
        System.out.println(schedule.getStart());
        System.out.println(schedule.getEnd());
        System.out.println("New Schedule: ");
        System.out.println(start);
        System.out.println(end);
        System.out.println("------");

        if (
          (schedule.getStart().isBefore(start) &&
            schedule.getEnd().isAfter(end)) ||
          (schedule.getStart().isAfter(start) &&
            schedule.getStart().isBefore(end)) ||
          (schedule.getEnd().isAfter(start) &&
            schedule.getEnd().isBefore(end)) ||
          schedule.getStart().equals(start) ||
          schedule.getEnd().equals(end)
        ) {
          return new ResponseEntity<>(
            new Message("Schedule conflict detected."),
            HttpStatus.CONFLICT
          );
        }
      }

      UltrasoundDoctor doctor = ultrasoundDoctorService
        .getById(doctorId)
        .orElse(null);
      WorkSchedule newSchedule = new WorkSchedule();
      newSchedule.setDate(date);
      newSchedule.setStart(start);
      newSchedule.setEnd(end);
      newSchedule.setUltrasoundDoctor(doctor);

      WorkSchedule savedSchedule = workScheduleService.save(newSchedule);

      return new ResponseEntity<>(
        new Message("WorkSchedules created successfully."),
        HttpStatus.OK
      );
    } else {
      List<WorkSchedule> schedules = workScheduleService.findByDoctorIdAndDate(
        doctorId,
        date
      );

      for (WorkSchedule schedule : schedules) {
        System.out.println("Schedule: ");
        System.out.println(schedule.getStart());
        System.out.println(schedule.getEnd());
        System.out.println("New Schedule: ");
        System.out.println(start);
        System.out.println(end);
        System.out.println("------");
        if (
          (schedule.getStart().isBefore(start) &&
            schedule.getEnd().isAfter(end)) ||
          (schedule.getStart().isAfter(start) &&
            schedule.getStart().isBefore(end)) ||
          (schedule.getEnd().isAfter(start) &&
            schedule.getEnd().isBefore(end)) ||
          schedule.getStart().equals(start) ||
          schedule.getEnd().equals(end)
        ) {
          return new ResponseEntity<>(
            new Message("Schedule conflict detected."),
            HttpStatus.CONFLICT
          );
        }
      }

      Doctor doctor = doctorService.getById(doctorId).orElse(null);
      WorkSchedule newSchedule = new WorkSchedule();
      newSchedule.setDate(date);
      newSchedule.setStart(start);
      newSchedule.setEnd(end);
      newSchedule.setDoctor(doctor);

      WorkSchedule savedSchedule = workScheduleService.save(newSchedule);

      return new ResponseEntity<>(
        new Message("WorkSchedules created successfully."),
        HttpStatus.OK
      );
    }
  }

  @PostMapping("/create-multiple")
  public ResponseEntity<?> addMultipleWorkSchedules(
    @RequestParam String doctorId,
    @RequestParam String from,
    @RequestParam String to,
    @RequestParam String start,
    @RequestParam String end,
    @RequestParam(required = false) DayOfWeek dayOfWeek
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalDate startDate = LocalDate.parse(from, formatter);
    LocalDate endDate = LocalDate.parse(to, formatter);
    LocalTime startTime = LocalTime.parse(start, formatter);
    LocalTime endTime = LocalTime.parse(end, formatter);

    if (!doctorService.existsById(UUID.fromString(doctorId))) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }

    Doctor doctor = doctorService
      .getById(UUID.fromString(doctorId))
      .orElse(null);
    if (doctor == null) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }

    for (
      LocalDate date = startDate;
      !date.isAfter(endDate);
      date = date.plusDays(1)
    ) {
      if (dayOfWeek == null || date.getDayOfWeek() == dayOfWeek) {
        WorkSchedule newSchedule = new WorkSchedule();
        newSchedule.setDate(date);
        newSchedule.setStart(startTime);
        newSchedule.setEnd(endTime);
        newSchedule.setDoctor(doctor);
        workScheduleService.save(newSchedule);
      }
    }

    return new ResponseEntity<>(
      new Message("Work schedules added successfully."),
      HttpStatus.OK
    );
  }

  @PostMapping("/interval")
  public ResponseEntity<?> createWorkSchedulesInInterval(
    @RequestParam UUID doctorId,
    @RequestParam @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate startDate,
    @RequestParam @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate endDate,
    @RequestParam DayOfWeek dayOfWeek,
    @RequestBody WorkScheduleDto workScheduleDto
  ) {
    if (!doctorService.existsById(doctorId)) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }

    Doctor doctor = doctorService.getById(doctorId).orElse(null);
    if (doctor == null) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }

    WorkSchedule workScheduleTemplate = new WorkSchedule();
    workScheduleTemplate.setStart(LocalTime.parse(workScheduleDto.getStart()));
    workScheduleTemplate.setEnd(LocalTime.parse(workScheduleDto.getEnd()));
    workScheduleTemplate.setDoctor(doctor);

    List<WorkSchedule> schedules =
      workScheduleService.createSchedulesInInterval(
        startDate,
        endDate,
        dayOfWeek,
        workScheduleTemplate
      );
    return ResponseEntity.ok(schedules);
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!workScheduleService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No work schedule found."),
        HttpStatus.NOT_FOUND
      );
    }
    WorkSchedule workSchedule = workScheduleService
      .getById(UUID.fromString(id))
      .orElse(null);

    if (workSchedule.getDoctor() != null) {
      Doctor doctor = workSchedule.getDoctor();
      ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");

      ZonedDateTime start = ZonedDateTime.of(
        workSchedule.getDate(),
        workSchedule.getStart(),
        zone
      );
      ZonedDateTime end = ZonedDateTime.of(
        workSchedule.getDate(),
        workSchedule.getEnd(),
        zone
      );
      List<AppointmentProjection> appointments =
        appointmentService.findAllByDoctorAndDateRange(
          doctor.getId(),
          start,
          end
        );

      for (AppointmentProjection appointmentProjection : appointments) {
        Appointment appointment = appointmentService
          .getById(appointmentProjection.getId())
          .orElse(null);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentService.save(appointment);

        emailService.cancelReminder(appointment.getId());

        emailService.sendNewMail(
          appointment.getPatient().getEmail(),
          "Turno cancelado",
          "Hola " +
          appointment.getPatient().getName() +
          ", te informamos que tu turno con el Dr. " +
          appointment.getDoctor().getLastname() +
          ", " +
          appointment.getDoctor().getName() +
          " para el " +
          appointment.getStart().toLocalDate() +
          " a las " +
          appointment.getStart().toLocalTime() +
          " ha sido cancelado."
        );
      }
    } else if (workSchedule.getUltrasoundDoctor() != null) {
      UltrasoundDoctor doctor = workSchedule.getUltrasoundDoctor();
      ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");

      ZonedDateTime start = ZonedDateTime.of(
        workSchedule.getDate(),
        workSchedule.getStart(),
        zone
      );
      ZonedDateTime end = ZonedDateTime.of(
        workSchedule.getDate(),
        workSchedule.getEnd(),
        zone
      );
      List<UltrasoundAppointmentProjection> appointments =
        ultrasoundAppointmentService.findAllByDoctorAndDateRange(
          doctor.getId(),
          start,
          end
        );

      for (UltrasoundAppointmentProjection appointmentProjection : appointments) {
        UltrasoundAppointment appointment = ultrasoundAppointmentService
          .getById(appointmentProjection.getId())
          .orElse(null);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        ultrasoundAppointmentService.save(appointment);

        emailService.cancelReminder(appointment.getId());

        emailService.sendNewMail(
          appointment.getPatient().getEmail(),
          "Turno cancelado",
          "Hola " +
          appointment.getPatient().getName() +
          ", te informamos que tu turno con el Dr. " +
          appointment.getUltrasoundDoctor().getLastname() +
          ", " +
          appointment.getUltrasoundDoctor().getName() +
          " para el " +
          appointment.getStart().toLocalDate() +
          " a las " +
          appointment.getStart().toLocalTime() +
          " ha sido cancelado."
        );
      }
    }

    workScheduleService.deleteById(UUID.fromString(id));
    return new ResponseEntity<>(
      new Message("Work schedule deleted."),
      HttpStatus.OK
    );
  }
}
