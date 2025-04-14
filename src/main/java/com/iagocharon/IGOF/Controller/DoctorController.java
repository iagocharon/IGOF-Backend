package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.DoctorSignupRequest;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Service.AuthService;
import com.iagocharon.IGOF.Service.DoctorService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.SpecialtyService;
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
@RequestMapping("/api/doctor")
public class DoctorController {

  @Autowired
  DoctorService doctorService;

  @Autowired
  SpecialtyService specialtyService;

  @Autowired
  AuthService authService;

  @Autowired
  InsuranceService insuranceService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<DoctorProjection> doctors = doctorService.getAll();
    return new ResponseEntity<>(doctors, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!doctorService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No doctor found."),
        HttpStatus.NOT_FOUND
      );
    }
    Doctor doctor = doctorService.getById(UUID.fromString(id)).get();
    return new ResponseEntity<>(doctor, HttpStatus.OK);
  }

  @GetMapping(value = "list/specialty")
  public ResponseEntity<?> listBySpecialty(
    @RequestParam String id,
    @RequestParam String insuranceId
  ) {
    if (!specialtyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No doctor found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (!insuranceService.existsById(UUID.fromString(insuranceId))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }
    List<DoctorProjection> doctors =
      doctorService.getAllBySpecialtyAndInsuranceId(
        UUID.fromString(id),
        UUID.fromString(insuranceId)
      );
    return new ResponseEntity<>(doctors, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody DoctorSignupRequest request) {
    for (String specialtyId : request.getSpecialtyIds()) {
      if (!specialtyService.existsById(UUID.fromString(specialtyId))) {
        return new ResponseEntity<>(
          new Message("No specialty found."),
          HttpStatus.NOT_FOUND
        );
      }
    }
    authService.createDoctor(request);
    return new ResponseEntity<>(
      new Message("Doctor created successfully."),
      HttpStatus.CREATED
    );
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestParam String id,
    @RequestBody DoctorSignupRequest request
  ) {
    if (!doctorService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No doctor found."),
        HttpStatus.NOT_FOUND
      );
    }
    Doctor doctor = doctorService.getById(UUID.fromString(id)).get();
    doctor.setUsername(request.getUsername());
    doctor.setEmail(request.getEmail());
    doctor.setName(request.getName());
    doctor.setLastname(request.getLastname());
    // Agregar verificación para specialtyIds
    List<Specialty> specialties = request.getSpecialtyIds() != null
      ? new ArrayList<>(
        request
          .getSpecialtyIds()
          .stream()
          .map(specialtyId ->
            specialtyService
              .getById(UUID.fromString(specialtyId))
              .orElseThrow(() ->
                new IllegalArgumentException(
                  "Specialty not found: " + specialtyId
                )
              )
          )
          .toList()
      ) // Envolver en new ArrayList<>()
      : new ArrayList<>();
    doctor.setSpecialties(specialties);

    final Doctor finalDoctor = doctor;
    specialties.forEach(specialty -> specialty.addDoctor(finalDoctor));
    // Verificar también insurancesIds
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
      ) // Envolver en ArrayList mutable
      : new ArrayList<>();
    doctor.setInsurances(insurances);

    insurances.forEach(insurance -> insurance.addDoctor(finalDoctor));
    doctor.setAppointmentDuration(request.getAppointmentDuration());

    doctor = doctorService.save(doctor);
    specialtyService.saveAll(specialties);
    insuranceService.saveAll(insurances);
    return new ResponseEntity<>(
      new Message("Doctor updated successfully."),
      HttpStatus.OK
    );
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!doctorService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No doctor found."),
        HttpStatus.NOT_FOUND
      );
    }
    doctorService.deleteById(UUID.fromString(id));
    return new ResponseEntity<>(new Message("Doctor deleted."), HttpStatus.OK);
  }
}
