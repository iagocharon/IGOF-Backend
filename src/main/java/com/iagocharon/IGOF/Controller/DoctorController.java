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
    System.out.println(request);
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
    doctor.setAppointmentDuration(request.getAppointmentDuration());

    // Synchronize specialties: add new ones and remove missing ones.
    if (request.getSpecialtyIds() != null) {
      // Convert incoming specialty ids from String to UUID
      List<UUID> incomingSpecialtyIds = request
        .getSpecialtyIds()
        .stream()
        .map(UUID::fromString)
        .toList();

      // For each specialty id in the request, add if not already present.
      for (String specialtyIdStr : request.getSpecialtyIds()) {
        UUID specialtyId = UUID.fromString(specialtyIdStr);
        if (!doctor.hasSpecialty(specialtyId)) {
          Specialty specialty = specialtyService
            .getById(specialtyId)
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Specialty not found: " + specialtyIdStr
              )
            );
          doctor.addSpecialty(specialty);
          specialty.addDoctor(doctor);
          specialtyService.save(specialty);
        }
      }

      // Remove any specialty from the doctor that is not present in the request.
      doctor
        .getSpecialties()
        .removeIf(specialty -> {
          if (!incomingSpecialtyIds.contains(specialty.getId())) {
            specialty.removeDoctor(doctor);
            specialtyService.save(specialty);
            return true;
          }
          return false;
        });
      doctorService.save(doctor);
    }

    // Synchronize insurances: add new ones and remove missing ones.
    if (request.getInsurancesIds() != null) {
      // Convert incoming insurance ids from String to UUID
      List<UUID> incomingInsurancesIds = request
        .getInsurancesIds()
        .stream()
        .map(UUID::fromString)
        .toList();

      // For each insurance id in the request, add if not already present.
      for (String insuranceIdStr : request.getInsurancesIds()) {
        UUID insuranceId = UUID.fromString(insuranceIdStr);
        if (!doctor.hasInsurance(insuranceId)) {
          Insurance insurance = insuranceService
            .getById(insuranceId)
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Insurance not found: " + insuranceIdStr
              )
            );
          doctor.addInsurance(insurance);
          insurance.addDoctor(doctor);
          insuranceService.save(insurance);
        }
      }

      // Remove any specialty from the doctor that is not present in the request.
      doctor
        .getInsurances()
        .removeIf(insurance -> {
          if (!incomingInsurancesIds.contains(insurance.getId())) {
            insurance.removeDoctor(doctor);
            insuranceService.save(insurance);
            return true;
          }
          return false;
        });
      doctorService.save(doctor);
    }

    doctorService.save(doctor);
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
