package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.AdminSignupRequest;
import com.iagocharon.IGOF.Dto.AuthResponse;
import com.iagocharon.IGOF.Dto.DoctorSignupRequest;
import com.iagocharon.IGOF.Dto.LoginRequest;
import com.iagocharon.IGOF.Dto.PatientSignupRequest;
import com.iagocharon.IGOF.Dto.UltrasoundDoctorSignupRequest;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.MedicalRecord;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.Role;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import com.iagocharon.IGOF.Entity.User;
import com.iagocharon.IGOF.Jwt.JwtService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

  @Autowired
  UltrasoundStudyService ultrasoundStudyService;

  @Autowired
  UltrasoundDoctorService ultrasoundDoctorService;

  public AuthResponse createPatient(PatientSignupRequest request) {
    if (userService.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("Username already exists");
    }
    if (userService.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalDate birthdate = LocalDate.parse(request.getBirthdate(), formatter);

    Patient user = new Patient();
    user.setUsername(request.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setRole(Role.PATIENT);
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setBirthdate(birthdate);
    user.setPhone(request.getPhone());
    user.setAddress(request.getAddress());
    user.setCity(request.getCity());
    user.setState(request.getState());
    user.setCountry(request.getCountry());
    user.setCitizenship(request.getCitizenship());
    user.setInsurance(request.getInsurance());
    user.setInsuranceNumber(request.getInsuranceNumber());

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
    if (request.getSpecialtyIds() != null) {
      // For each specialty id in the request, add if not already present.
      for (String specialtyIdStr : request.getSpecialtyIds()) {
        UUID specialtyId = UUID.fromString(specialtyIdStr);
        if (!user.hasSpecialty(specialtyId)) {
          Specialty specialty = specialtyService
            .getById(specialtyId)
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Specialty not found: " + specialtyIdStr
              )
            );
          user.addSpecialty(specialty);
          specialty.addDoctor(user);
          specialtyService.save(specialty);
        }
      }

      doctorService.save(user);
    }

    if (request.getInsurancesIds() != null) {
      // For each insurance id in the request, add if not already present.
      for (String insuranceIdStr : request.getInsurancesIds()) {
        UUID insuranceId = UUID.fromString(insuranceIdStr);
        if (!user.hasInsurance(insuranceId)) {
          Insurance insurance = insuranceService
            .getById(insuranceId)
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Insurance not found: " + insuranceIdStr
              )
            );
          user.addInsurance(insurance);
          insurance.addDoctor(user);
          insuranceService.save(insurance);
        }
      }

      doctorService.save(user);
    }

    user = doctorService.save(user);

    var jwt = jwtService.generateToken(user);
    return new AuthResponse(
      jwt,
      user.getId(),
      user.getRole(),
      user.getUsername()
    );
  }

  public AuthResponse createUltrasoundDoctor(
    UltrasoundDoctorSignupRequest request
  ) {
    UltrasoundDoctor user = new UltrasoundDoctor();
    user.setUsername(request.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setRole(Role.ULTRASOUND_DOCTOR);
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setAppointmentDuration(request.getAppointmentDuration());
    if (request.getUltrasoundStudiesIds() != null) {
      // For each ultrasoundStudy id in the request, add if not already present.
      for (String ultrasoundStudyIdStr : request.getUltrasoundStudiesIds()) {
        UUID ultrasoundStudyId = UUID.fromString(ultrasoundStudyIdStr);
        if (!user.hasUltrasoundStudy(ultrasoundStudyId)) {
          UltrasoundStudy ultrasoundStudy = ultrasoundStudyService
            .getById(ultrasoundStudyId)
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Study not found: " + ultrasoundStudyIdStr
              )
            );
          user.addUltrasoundStudy(ultrasoundStudy);
          ultrasoundStudy.addUltrasoundDoctor(user);
          ultrasoundStudyService.save(ultrasoundStudy);
        }
      }

      ultrasoundDoctorService.save(user);
    }

    if (request.getInsurancesIds() != null) {
      // For each specialty id in the request, add if not already present.
      for (String insuranceIdStr : request.getInsurancesIds()) {
        UUID insuranceId = UUID.fromString(insuranceIdStr);
        if (!user.hasInsurance(insuranceId)) {
          Insurance insurance = insuranceService
            .getById(insuranceId)
            .orElseThrow(() ->
              new IllegalArgumentException(
                "Insurance not found: " + insuranceIdStr
              )
            );
          user.addInsurance(insurance);
          insurance.addUltrasoundDoctor(user);
          insuranceService.save(insurance);
        }
      }

      ultrasoundDoctorService.save(user);
    }

    user = ultrasoundDoctorService.save(user);
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
    System.out.println(
      "Login request: " +
      loginRequest.getUsername() +
      ", " +
      loginRequest.getPassword()
    );
    User user = userService
      .getByUsername(loginRequest.getUsername().toLowerCase())
      .get();

    System.out.println("User found: " + user.getUsername());
    if (
      !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())
    ) {
      System.out.println("Invalid password");
      throw new BadCredentialsException("Invalid password");
    }
    var jwt = jwtService.generateToken(user);
    System.out.println("JWT generated: " + jwt);
    return new AuthResponse(
      jwt,
      user.getId(),
      user.getRole(),
      user.getUsername()
    );
  }
}
