package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.AppointmentDto;
import com.iagocharon.IGOF.Dto.ConfirmAppointmentDto;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.AppointmentProjection;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.AppointmentStatus;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.PaymentMethod;
import com.iagocharon.IGOF.Service.AppointmentService;
import com.iagocharon.IGOF.Service.DoctorService;
import com.iagocharon.IGOF.Service.EmailService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.PatientService;
import com.iagocharon.IGOF.Service.PaymentMethodService;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

  @Autowired
  AppointmentService appointmentService;

  @Autowired
  DoctorService doctorService;

  @Autowired
  PatientService patientService;

  @Autowired
  PaymentMethodService paymentMethodService;

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  EmailService emailService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<Appointment> appointments = appointmentService.getAll();
    return new ResponseEntity<>(appointments, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!appointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    Appointment appointment = appointmentService
      .getById(UUID.fromString(id))
      .get();
    return new ResponseEntity<>(appointment, HttpStatus.OK);
  }

  @GetMapping(value = "list/doctor")
  public ResponseEntity<?> listByDoctor(
    @RequestParam String id,
    @RequestParam String from,
    @RequestParam String to
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    ZonedDateTime startDate = ZonedDateTime.parse(from, formatter);
    ZonedDateTime endDate = ZonedDateTime.parse(to, formatter);

    List<AppointmentProjection> appointments =
      appointmentService.findAllByDoctorAndDateRange(
        UUID.fromString(id),
        startDate,
        endDate
      );
    return new ResponseEntity<>(appointments, HttpStatus.OK);
  }

  @GetMapping(value = "list/patient")
  public ResponseEntity<?> listByPatient(
    @RequestParam String id,
    @RequestParam String from,
    @RequestParam String to
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    ZonedDateTime startDate = ZonedDateTime.parse(from, formatter);
    ZonedDateTime endDate = ZonedDateTime.parse(to, formatter);

    List<AppointmentProjection> appointments =
      appointmentService.findAllByPatientAndDateRange(
        UUID.fromString(id),
        startDate,
        endDate
      );
    Patient patient = patientService.getById(UUID.fromString(id)).get();
    for (Patient child : patient.getChildrenPatients()) {
      List<AppointmentProjection> childAppointments =
        appointmentService.findAllByPatientAndDateRange(
          child.getId(),
          startDate,
          endDate
        );
      appointments.addAll(childAppointments);
    }
    return new ResponseEntity<>(appointments, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody AppointmentDto appointmentDto) {
    if (
      !doctorService.existsById(UUID.fromString(appointmentDto.getDoctorId()))
    ) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !patientService.existsById(UUID.fromString(appointmentDto.getPatientId()))
    ) {
      return new ResponseEntity<>(
        new Message("Patient not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !insuranceService.existsById(
        UUID.fromString(appointmentDto.getInsuranceId())
      )
    ) {
      System.out.println("Insurance not found.");
      return new ResponseEntity<>(
        new Message("Insurance not found."),
        HttpStatus.NOT_FOUND
      );
    }

    Appointment appointment = new Appointment();
    Doctor doctor = doctorService
      .getById(UUID.fromString(appointmentDto.getDoctorId()))
      .get();
    Patient patient = patientService
      .getById(UUID.fromString(appointmentDto.getPatientId()))
      .get();

    Insurance insurance = insuranceService
      .getById(UUID.fromString(appointmentDto.getInsuranceId()))
      .get();

    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    ZonedDateTime startDate = ZonedDateTime.parse(
      appointmentDto.getStart(),
      formatter
    );
    ZonedDateTime endDate = ZonedDateTime.parse(
      appointmentDto.getEnd(),
      formatter
    );

    appointment.setStart(startDate);
    appointment.setEnd(endDate);
    appointment.setDoctor(doctor);
    appointment.setPatient(patient);
    appointment.setInsurance(insurance);

    appointmentService.save(appointment);

    doctor.addAppointment(appointment);
    doctorService.save(doctor);
    patient.addAppointment(appointment);
    patientService.save(patient);
    insurance.addAppointment(appointment);
    insuranceService.save(insurance);

    String subject = "Confirmación de turno";
    String body = String.format(
      "Hola %s,\n\nTu turno con el Dr. %s, %s ha sido confirmado para el %s a las %s. \n\nRecordá que en caso de no poder asistir al turno, podés cancelarlo desde el portal de autogestión hasta 24 horas antes del mismo en el siguiente link:\n<a href='https://pacientes.igof.com.ar'>Portal de Autogestión</a>.\n\nSaludos, \nClínica IGOF",
      patient.getName(),
      doctor.getLastname(),
      doctor.getName(),
      appointment.getStart().toLocalDate(),
      appointment.getStart().toLocalTime()
    );
    emailService.sendNewMail(patient.getEmail(), subject, body);

    emailService.scheduleReminder(appointment);

    return new ResponseEntity<>(
      new Message("Appointment created successfully."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestBody ConfirmAppointmentDto appointmentDto,
    @RequestParam String id
  ) {
    if (!appointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (appointmentDto.getPaymentMethodId() != null) {
      if (
        !paymentMethodService.existsById(
          UUID.fromString(appointmentDto.getPaymentMethodId())
        )
      ) {
        return new ResponseEntity<>(
          new Message("Payment method not found."),
          HttpStatus.NOT_FOUND
        );
      }
    }
    if (appointmentDto.getSurgeryPaymentMethodId() != null) {
      if (
        !paymentMethodService.existsById(
          UUID.fromString(appointmentDto.getSurgeryPaymentMethodId())
        )
      ) {
        return new ResponseEntity<>(
          new Message("Payment method not found."),
          HttpStatus.NOT_FOUND
        );
      }
    }

    Appointment appointment = appointmentService
      .getById(UUID.fromString(id))
      .get();

    PaymentMethod previousPaymentMethod = null;
    if (appointment.getPaymentMethod() != null) {
      previousPaymentMethod = appointment.getPaymentMethod();
    }
    PaymentMethod previousSurgeryPaymentMethod = null;
    if (appointment.getSurgeryPaymentMethod() != null) {
      previousSurgeryPaymentMethod = appointment.getSurgeryPaymentMethod();
    }
    PaymentMethod paymentMethod = null;
    if (appointmentDto.getPaymentMethodId() != null) {
      paymentMethod = paymentMethodService
        .getById(UUID.fromString(appointmentDto.getPaymentMethodId()))
        .get();
    }
    PaymentMethod surgeryPaymentMethod = null;
    if (appointmentDto.getSurgeryPaymentMethodId() != null) {
      surgeryPaymentMethod = paymentMethodService
        .getById(UUID.fromString(appointmentDto.getSurgeryPaymentMethodId()))
        .get();
    }

    if (appointmentDto.getStatus() != null) {
      appointment.setStatus(
        AppointmentStatus.valueOf(appointmentDto.getStatus())
      );
    }
    appointment.setPlus(appointmentDto.getPlus());
    appointment.setPaymentMethod(paymentMethod);
    appointment.setSurgeryPlus(appointmentDto.getSurgeryPlus());
    appointment.setSurgeryPaymentMethod(surgeryPaymentMethod);

    appointmentService.save(appointment);
    if (previousPaymentMethod != null) {
      previousPaymentMethod.removeAppointment(appointment);
      paymentMethodService.save(previousPaymentMethod);
    }
    if (previousSurgeryPaymentMethod != null) {
      previousSurgeryPaymentMethod.removeAppointment(appointment);
      paymentMethodService.save(previousSurgeryPaymentMethod);
    }

    if (paymentMethod != null) {
      paymentMethod.addAppointment(appointment);
      paymentMethodService.save(paymentMethod);
    }
    if (surgeryPaymentMethod != null) {
      surgeryPaymentMethod.addSurgeryAppointment(appointment);
      paymentMethodService.save(surgeryPaymentMethod);
    }

    return new ResponseEntity<>(
      new Message("Appointment updated successfully."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "arrival")
  public ResponseEntity<?> setArrival(@RequestParam String id) {
    if (!appointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    Appointment appointment = appointmentService
      .getById(UUID.fromString(id))
      .get();
    appointment.setArrival(ZonedDateTime.now());
    appointmentService.save(appointment);

    return new ResponseEntity<>(
      new Message("Arrival time set successfully."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "cancel")
  public ResponseEntity<?> toggleCancel(@RequestParam String id) {
    if (!appointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    Appointment appointment = appointmentService
      .getById(UUID.fromString(id))
      .get();
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

    return new ResponseEntity<>(
      new Message("Appointment cancelled successfully."),
      HttpStatus.OK
    );
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!appointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    Appointment appointment = appointmentService
      .getById(UUID.fromString(id))
      .get();
    appointmentService.delete(appointment);
    return new ResponseEntity<>(
      new Message("Appointment deleted."),
      HttpStatus.OK
    );
  }
}
