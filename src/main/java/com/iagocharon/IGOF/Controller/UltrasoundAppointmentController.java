package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.ConfirmUltrasoundAppointmentDto;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.UltrasoundAppointmentProjection;
import com.iagocharon.IGOF.Dto.UltrasoundAppointmentDto;
import com.iagocharon.IGOF.Dto.UltrasoundStudyStatusDto;
import com.iagocharon.IGOF.Entity.AppointmentStatus;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.PaymentMethod;
import com.iagocharon.IGOF.Entity.StudyStatus;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudyStatus;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.PatientService;
import com.iagocharon.IGOF.Service.PaymentMethodService;
import com.iagocharon.IGOF.Service.UltrasoundAppointmentService;
import com.iagocharon.IGOF.Service.UltrasoundDoctorService;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
@RequestMapping("/api/ultrasound-appointment")
public class UltrasoundAppointmentController {

  @Autowired
  UltrasoundAppointmentService ultrasoundAppointmentService;

  @Autowired
  UltrasoundDoctorService ultrasoundDoctorService;

  @Autowired
  PatientService patientService;

  @Autowired
  PaymentMethodService paymentMethodService;

  @Autowired
  InsuranceService insuranceService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    return ResponseEntity.ok(ultrasoundAppointmentService.getAll());
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!ultrasoundAppointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    UltrasoundAppointment appointment = ultrasoundAppointmentService
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

    List<UltrasoundAppointmentProjection> appointments =
      ultrasoundAppointmentService.findAllByDoctorAndDateRange(
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

    List<UltrasoundAppointmentProjection> appointments =
      ultrasoundAppointmentService.findAllByPatientAndDateRange(
        UUID.fromString(id),
        startDate,
        endDate
      );

    return new ResponseEntity<>(appointments, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(
    @RequestBody UltrasoundAppointmentDto ultrasoundAppointmentDto
  ) {
    if (
      !ultrasoundDoctorService.existsById(
        UUID.fromString(ultrasoundAppointmentDto.getUltrasoundDoctorId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !patientService.existsById(
        UUID.fromString(ultrasoundAppointmentDto.getPatientId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("Patient not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !insuranceService.existsById(
        UUID.fromString(ultrasoundAppointmentDto.getInsuranceId())
      )
    ) {}

    UltrasoundAppointment ultrasoundAppointment = new UltrasoundAppointment();
    UltrasoundDoctor ultrasoundDoctor = ultrasoundDoctorService
      .getById(
        UUID.fromString(ultrasoundAppointmentDto.getUltrasoundDoctorId())
      )
      .get();
    Patient patient = patientService
      .getById(UUID.fromString(ultrasoundAppointmentDto.getPatientId()))
      .get();
    Insurance insurance = insuranceService
      .getById(UUID.fromString(ultrasoundAppointmentDto.getInsuranceId()))
      .get();

    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    ZonedDateTime start = ZonedDateTime.parse(
      ultrasoundAppointmentDto.getStart(),
      formatter
    );
    ZonedDateTime end = ZonedDateTime.parse(
      ultrasoundAppointmentDto.getEnd(),
      formatter
    );

    ultrasoundAppointment.setStart(start);
    ultrasoundAppointment.setEnd(end);
    ultrasoundAppointment.setUltrasoundDoctor(ultrasoundDoctor);
    ultrasoundAppointment.setPatient(patient);
    ultrasoundAppointment.setInsurance(insurance);

    ultrasoundAppointmentService.save(ultrasoundAppointment);

    ultrasoundDoctor.addUltrasoundAppointment(ultrasoundAppointment);
    ultrasoundDoctorService.save(ultrasoundDoctor);
    patient.addUltrasoundAppointment(ultrasoundAppointment);
    patientService.save(patient);
    insurance.addUltrasoundAppointment(ultrasoundAppointment);
    insuranceService.save(insurance);

    return new ResponseEntity<>(
      new Message("Appointment created successfully."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestBody ConfirmUltrasoundAppointmentDto confirmUltrasoundAppointmentDto,
    @RequestParam String id
  ) {
    System.out.println(confirmUltrasoundAppointmentDto.toString());
    if (!ultrasoundAppointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (confirmUltrasoundAppointmentDto.getPaymentMethodId() != null) {
      if (
        !paymentMethodService.existsById(
          UUID.fromString(confirmUltrasoundAppointmentDto.getPaymentMethodId())
        )
      ) {
        return new ResponseEntity<>(
          new Message("Payment method not found."),
          HttpStatus.NOT_FOUND
        );
      }
    }

    UltrasoundAppointment ultrasoundAppointment = ultrasoundAppointmentService
      .getById(UUID.fromString(id))
      .get();
    PaymentMethod previousPaymentMethod = null;
    if (ultrasoundAppointment.getPaymentMethod() != null) {
      previousPaymentMethod = ultrasoundAppointment.getPaymentMethod();
    }
    PaymentMethod paymentMethod = null;
    if (confirmUltrasoundAppointmentDto.getPaymentMethodId() != null) {
      paymentMethod = paymentMethodService
        .getById(
          UUID.fromString(confirmUltrasoundAppointmentDto.getPaymentMethodId())
        )
        .get();
    }
    if (confirmUltrasoundAppointmentDto.getStatus() != null) {
      ultrasoundAppointment.setStatus(
        AppointmentStatus.valueOf(confirmUltrasoundAppointmentDto.getStatus())
      );
    }

    ultrasoundAppointmentService.save(ultrasoundAppointment);
    if (previousPaymentMethod != null) {
      previousPaymentMethod.removeUltrasoundAppointment(ultrasoundAppointment);
      paymentMethodService.save(previousPaymentMethod);
    }
    if (paymentMethod != null) {
      paymentMethod.addUltrasoundAppointment(ultrasoundAppointment);
      paymentMethodService.save(paymentMethod);
    }

    ultrasoundAppointment.setPlus(confirmUltrasoundAppointmentDto.getPlus());
    ultrasoundAppointment.setPaymentMethod(paymentMethod);
    ultrasoundAppointmentService.clearStatuses(ultrasoundAppointment);
    List<UltrasoundStudyStatus> ultrasoundStudyStatuses = new ArrayList<>();
    for (UltrasoundStudyStatusDto ultrasoundStudyStatusDto : confirmUltrasoundAppointmentDto.getUltrasoundStudyStatus()) {
      System.out.println(ultrasoundStudyStatusDto.toString());
      UltrasoundStudyStatus ultrasoundStudyStatus = new UltrasoundStudyStatus();
      ultrasoundStudyStatus.setUltrasoundAppointment(ultrasoundAppointment);
      ultrasoundStudyStatus.setName(ultrasoundStudyStatusDto.getName());
      ultrasoundStudyStatus.setReason(ultrasoundStudyStatusDto.getReason());
      ultrasoundStudyStatus.setStatus(
        StudyStatus.valueOf(ultrasoundStudyStatusDto.getStatus())
      );
      ultrasoundStudyStatuses.add(ultrasoundStudyStatus);
    }

    ultrasoundAppointmentService.saveWithStatuses(
      ultrasoundAppointment,
      ultrasoundStudyStatuses
    );

    return new ResponseEntity<>(
      new Message("Appointment updated successfully."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "cancel")
  public ResponseEntity<?> cancel(@RequestParam String id) {
    if (!ultrasoundAppointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    UltrasoundAppointment ultrasoundAppointment = ultrasoundAppointmentService
      .getById(UUID.fromString(id))
      .get();
    ultrasoundAppointment.setStatus(AppointmentStatus.CANCELLED);
    ultrasoundAppointmentService.save(ultrasoundAppointment);
    return new ResponseEntity<>(
      new Message("Appointment cancelled successfully."),
      HttpStatus.OK
    );
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!ultrasoundAppointmentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No appointment found."),
        HttpStatus.NOT_FOUND
      );
    }
    ultrasoundAppointmentService.deleteById(UUID.fromString(id));
    return new ResponseEntity<>(
      new Message("Appointment deleted successfully."),
      HttpStatus.OK
    );
  }
}
