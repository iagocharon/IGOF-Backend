package com.iagocharon.IGOF.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Dto.AdminSignupRequest;
import com.iagocharon.IGOF.Dto.AuthResponse;
import com.iagocharon.IGOF.Dto.DoctorSignupRequest;
import com.iagocharon.IGOF.Dto.LoginRequest;
import com.iagocharon.IGOF.Dto.PatientSignupRequest;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.MedicalRecord;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.Role;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Entity.User;
import com.iagocharon.IGOF.Jwt.JwtService;

@Service
public class AuthService {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtService jwtService;

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  PatientService patientService;

  @Autowired
  DoctorService doctorService;

  @Autowired
  SpecialtyService specialtyService;

  @Autowired
  MedicalRecordService medicalRecordService;

  public AuthResponse createPatient(PatientSignupRequest request) {
    Patient user = new Patient();
    user.setUsername(request.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setRole(Role.PATIENT);
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setBirthdate(request.getBirthdate());
    user.setPhone(request.getPhone());

    user = patientService.save(user);

    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setPatient(user);
    user.setMedicalRecord(medicalRecord);
    medicalRecordService.save(medicalRecord);

    String jwt = jwtService.generateToken(user);

    return new AuthResponse(
      jwt,
      user.getId(),
      user.getRole(),
      user.getUsername()
    );
  }

  public AuthResponse createDoctor(DoctorSignupRequest request) {
    Doctor user = new Doctor();
    user.setUsername(request.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setRole(Role.DOCTOR);
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setAppointmentDuration(request.getAppointmentDuration());
    List<Specialty> specialties = request
      .getSpecialtyIds()
      .stream()
      .map(id ->
        specialtyService
          .getById(UUID.fromString(id))
          .orElseThrow(() ->
            new IllegalArgumentException("Specialty not found: " + id)
          )
      )
      .toList();

    user.setSpecialties(specialties);
    final Doctor finalUser = user;
    specialties.forEach(specialty -> specialty.addDoctor(finalUser));

    List<Insurance> insurances = request
      .getInsurancesIds()
      .stream()
      .map(id ->
        insuranceService
          .getById(UUID.fromString(id))
          .orElseThrow(() ->
            new IllegalArgumentException("Insurance not found: " + id)
          )
      )
      .toList();

    user.setInsurances(insurances);
    insurances.forEach(insurance -> insurance.addDoctor(finalUser));

    user = doctorService.save(user);
    specialtyService.saveAll(specialties);
    insuranceService.saveAll(insurances);

    var jwt = jwtService.generateToken(user);
    return new AuthResponse(
      jwt,
      user.getId(),
      user.getRole(),
      user.getUsername()
    );
  }

  public AuthResponse createAdmin(AdminSignupRequest request) {
    User user = new User();
    user.setUsername(request.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setRole(Role.ADMIN);
    user.setName(request.getName());
    user.setLastname(request.getLastname());

    user = userService.save(user);
    var jwt = jwtService.generateToken(user);
    return new AuthResponse(
      jwt,
      user.getId(),
      user.getRole(),
      user.getUsername()
    );
  }

  public AuthResponse login(LoginRequest loginRequest) {
    User user = userService
      .getByUsername(loginRequest.getUsername().toLowerCase())
      .get();
    if (
      !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())
    ) {
      throw new BadCredentialsException("Invalid password");
    }
    var jwt = jwtService.generateToken(user);
    return new AuthResponse(
      jwt,
      user.getId(),
      user.getRole(),
      user.getUsername()
    );
  }
}
