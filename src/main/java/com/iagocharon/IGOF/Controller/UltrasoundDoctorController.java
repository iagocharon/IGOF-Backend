package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.UltrasoundDoctorProjection;
import com.iagocharon.IGOF.Dto.UltrasoundDoctorSignupRequest;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import com.iagocharon.IGOF.Service.AuthService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.UltrasoundDoctorService;
import com.iagocharon.IGOF.Service.UltrasoundStudyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ultrasound-doctor")
public class UltrasoundDoctorController {

  @Autowired
  UltrasoundDoctorService ultrasoundDoctorService;

  @Autowired
  UltrasoundStudyService ultrasoundStudyService;

  @Autowired
  AuthService authService;

  @Autowired
  InsuranceService insuranceService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<UltrasoundDoctorProjection> doctors =
      ultrasoundDoctorService.findAllProjectedBy();
    return new ResponseEntity<>(doctors, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!ultrasoundDoctorService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>("No doctor found.", HttpStatus.NOT_FOUND);
    }
    UltrasoundDoctor doctor = ultrasoundDoctorService
      .getById(UUID.fromString(id))
      .get();
    return new ResponseEntity<>(doctor, HttpStatus.OK);
  }

  @GetMapping(value = "list/study")
  public ResponseEntity<?> listStudy(
    @RequestParam String id,
    @RequestParam String insuranceId
  ) {
    if (!ultrasoundStudyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>("No study found.", HttpStatus.NOT_FOUND);
    }
    if (!insuranceService.existsById(UUID.fromString(insuranceId))) {
      return new ResponseEntity<>("No insurance found.", HttpStatus.NOT_FOUND);
    }
    List<UltrasoundDoctorProjection> doctors =
      ultrasoundDoctorService.findAllProjectedByStudyIdAndInsuranceId(
        UUID.fromString(id),
        UUID.fromString(insuranceId)
      );

    return new ResponseEntity<>(doctors, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(
    @RequestBody UltrasoundDoctorSignupRequest request
  ) {
    for (String studyId : request.getUltrasoundStudiesIds()) {
      if (!ultrasoundStudyService.existsById(UUID.fromString(studyId))) {
        return new ResponseEntity<>("No study found.", HttpStatus.NOT_FOUND);
      }
    }
    authService.createUltrasoundDoctor(request);

    return new ResponseEntity<>("Doctor created.", HttpStatus.OK);
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestParam String id,
    @RequestBody UltrasoundDoctorSignupRequest request
  ) {
    if (!ultrasoundDoctorService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>("No doctor found.", HttpStatus.NOT_FOUND);
    }

    UltrasoundDoctor doctor = ultrasoundDoctorService
      .getById(UUID.fromString(id))
      .get();
    doctor.setUsername(request.getUsername());
    doctor.setEmail(request.getEmail());
    doctor.setName(request.getName());
    doctor.setLastname(request.getLastname());
    List<UltrasoundStudy> studies = new ArrayList<>(
      request
        .getUltrasoundStudiesIds()
        .stream()
        .map(studyId ->
          ultrasoundStudyService
            .getById(UUID.fromString(studyId))
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Ultrasound study not found: " + studyId
              )
            )
        )
        .toList()
    );
    doctor.setUltrasoundStudies(studies);
    final UltrasoundDoctor finalDoctor = doctor;
    studies.forEach(study -> study.addUltrasoundDoctor(finalDoctor));

    List<Insurance> insurances = request.getInsurancesIds() != null
      ? new ArrayList<>(
        request
          .getInsurancesIds()
          .stream()
          .map(insuranceId ->
            insuranceService
              .getById(UUID.fromString(insuranceId))
              .orElseThrow(() ->
                new IllegalArgumentException(
                  "Insurance not found: " + insuranceId
                )
              )
          )
          .toList()
      )
      : new ArrayList<>();
    doctor.setInsurances(insurances);
    insurances.forEach(insurance -> insurance.addUltrasoundDoctor(finalDoctor));
    doctor.setAppointmentDuration(request.getAppointmentDuration());

    doctor = ultrasoundDoctorService.save(doctor);
    ultrasoundStudyService.saveAll(studies);
    insuranceService.saveAll(insurances);

    return new ResponseEntity<>(new Message("Doctor updated."), HttpStatus.OK);
  }
}
