package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.EvolutionDto;
import com.iagocharon.IGOF.Dto.MedicalRecordDto;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.PatientDto;
import com.iagocharon.IGOF.Dto.Projections.PatientProjection;
import com.iagocharon.IGOF.Dto.UltrasoundStudyReportDto;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.AppointmentStatus;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Evolution;
import com.iagocharon.IGOF.Entity.MedicalRecord;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.Role;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudyReport;
import com.iagocharon.IGOF.Service.AppointmentService;
import com.iagocharon.IGOF.Service.DoctorService;
import com.iagocharon.IGOF.Service.EvolutionService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.MedicalRecordService;
import com.iagocharon.IGOF.Service.PatientService;
import com.iagocharon.IGOF.Service.UltrasoundAppointmentService;
import com.iagocharon.IGOF.Service.UltrasoundDoctorService;
import com.iagocharon.IGOF.Service.UltrasoundStudyReportService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

  @Autowired
  PatientService patientService;

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  DoctorService doctorService;

  @Autowired
  MedicalRecordService medicalRecordService;

  @Autowired
  EvolutionService evolutionService;

  @Autowired
  AppointmentService appointmentService;

  @Autowired
  UltrasoundDoctorService ultrasoundDoctorService;

  @Autowired
  UltrasoundAppointmentService ultrasoundAppointmentService;

  @Autowired
  UltrasoundStudyReportService ultrasoundStudyReportService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<PatientProjection> patients = patientService.getAll();
    return new ResponseEntity<>(patients, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!patientService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("User not found."),
        HttpStatus.NOT_FOUND
      );
    }
    Patient patient = patientService.getById(UUID.fromString(id)).get();
    return new ResponseEntity<>(patient, HttpStatus.OK);
  }

  @GetMapping(value = "children")
  public ResponseEntity<?> getChildren(Authentication authentication) {
    Patient parent = patientService
      .getByUsername(authentication.getName())
      .get();
    List<Patient> children = parent.getChildrenPatients();
    children.add(parent);
    return new ResponseEntity<>(children, HttpStatus.OK);
  }

  @GetMapping(value = "medical-record")
  public ResponseEntity<?> medicalRecord(@RequestParam String id) {
    if (!patientService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("User not found."),
        HttpStatus.NOT_FOUND
      );
    }
    Patient patient = patientService.getById(UUID.fromString(id)).get();

    return new ResponseEntity<>(patient.getMedicalRecord(), HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody PatientDto patientDto) {
    if (patientService.existsByUsername(patientDto.getUsername())) {
      return new ResponseEntity<>(
        new Message("Username already in use."),
        HttpStatus.BAD_REQUEST
      );
    }
    if (patientService.existsByEmail(patientDto.getEmail())) {
      return new ResponseEntity<>(
        new Message("Email already in use."),
        HttpStatus.BAD_REQUEST
      );
    }

    Patient patient = new Patient();
    patient.setUsername(patientDto.getUsername());
    patient.setEmail(patientDto.getEmail());
    patient.setName(patientDto.getName());
    patient.setLastname(patientDto.getLastname());
    patient.setBirthdate(patientDto.getBirthdate());
    patient.setPhone(patientDto.getPhone());
    patient.setAddress(patientDto.getAddress());
    patient.setCity(patientDto.getCity());
    patient.setState(patientDto.getState());
    patient.setCountry(patientDto.getCountry());
    patient.setCitizenship(patientDto.getCitizenship());
    patient.setInsurance(patientDto.getInsurance());
    patient.setInsuranceNumber(patientDto.getInsuranceNumber());
    patient.setRole(Role.PATIENT);

    patientService.save(patient);

    return new ResponseEntity<>(new Message("User created."), HttpStatus.OK);
  }

  @PostMapping(value = "create-child")
  public ResponseEntity<?> createChild(
    @RequestBody PatientDto patientDto,
    Authentication authentication
  ) {
    Patient parent = patientService
      .getByUsername(authentication.getName())
      .get();

    Patient patient = new Patient();
    patient.setName(patientDto.getName());
    patient.setLastname(patientDto.getLastname());
    patient.setBirthdate(patientDto.getBirthdate());
    patient.setPhone(patientDto.getPhone());
    patient.setAddress(patientDto.getAddress());
    patient.setCity(patientDto.getCity());
    patient.setState(patientDto.getState());
    patient.setCountry(patientDto.getCountry());
    patient.setCitizenship(patientDto.getCitizenship());
    patient.setInsurance(patientDto.getInsurance());
    patient.setInsuranceNumber(patientDto.getInsuranceNumber());
    patient.setRole(Role.PATIENT);
    patient.setParentPatient(parent);
    patient = patientService.save(patient);
    parent.addChildrenPatient(patient);
    patientService.save(parent);

    return new ResponseEntity<>(new Message("User created."), HttpStatus.OK);
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestBody PatientDto patientDto,
    @RequestParam String id
  ) {
    if (!patientService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("User not found."),
        HttpStatus.NOT_FOUND
      );
    }
    Patient patient = patientService.getById(UUID.fromString(id)).get();
    patient.setAddress(patientDto.getAddress());
    patient.setBirthdate(patientDto.getBirthdate());
    patient.setCity(patientDto.getCity());
    patient.setCitizenship(patientDto.getCitizenship());
    patient.setCountry(patientDto.getCountry());
    if (patientDto.getEmail() != null) {
      patient.setEmail(patientDto.getEmail());
    }
    if (patientDto.getUsername() != null) {
      patient.setUsername(patientDto.getUsername());
    }
    patient.setLastname(patientDto.getLastname());
    patient.setName(patientDto.getName());
    patient.setPhone(patientDto.getPhone());
    patient.setState(patientDto.getState());
    patient.setInsurance(patientDto.getInsurance());
    patient.setInsuranceNumber(patientDto.getInsuranceNumber());
    patientService.save(patient);
    return new ResponseEntity<>(new Message("User updated."), HttpStatus.OK);
  }

  @PutMapping(value = "update-medical-record")
  public ResponseEntity<?> updateMedicalRecord(
    @RequestBody MedicalRecordDto medicalRecordDto,
    @RequestParam String id
  ) {
    if (!patientService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("User not found."),
        HttpStatus.NOT_FOUND
      );
    }
    Patient patient = patientService.getById(UUID.fromString(id)).get();
    MedicalRecord medicalRecord = patient.getMedicalRecord();
    medicalRecord.setAsmaFam(medicalRecordDto.getAsmaFam());
    medicalRecord.setHtaFam(medicalRecordDto.getHtaFam());
    medicalRecord.setDiabetesFam(medicalRecordDto.getDiabetesFam());
    medicalRecord.setDislipidemiaFam(medicalRecordDto.getDislipidemiaFam());
    medicalRecord.setIamFam(medicalRecordDto.getIamFam());
    medicalRecord.setCardiopatiaIsquemicaFam(
      medicalRecordDto.getCardiopatiaIsquemicaFam()
    );
    medicalRecord.setAcvIsquemicoFam(medicalRecordDto.getAcvIsquemicoFam());
    medicalRecord.setAcvHemorragicoFam(medicalRecordDto.getAcvHemorragicoFam());
    medicalRecord.setCancerColonFam(medicalRecordDto.getCancerColonFam());
    medicalRecord.setCancerPulmonFam(medicalRecordDto.getCancerPulmonFam());
    medicalRecord.setCancerMamaFam(medicalRecordDto.getCancerMamaFam());
    medicalRecord.setCancerOvarioFam(medicalRecordDto.getCancerOvarioFam());
    medicalRecord.setCancerProstataFam(medicalRecordDto.getCancerProstataFam());
    medicalRecord.setCancerRinonFam(medicalRecordDto.getCancerRinonFam());
    medicalRecord.setEnfermedadTiroideaFam(
      medicalRecordDto.getEnfermedadTiroideaFam()
    );
    medicalRecord.setOtrosAntecedentesFam(
      medicalRecordDto.getOtrosAntecedentesFam()
    );
    medicalRecord.setObservacionesFam(medicalRecordDto.getObservacionesFam());
    medicalRecord.setHiv(medicalRecordDto.getHiv());
    medicalRecord.setTabaquismo(medicalRecordDto.getTabaquismo());
    medicalRecord.setAlcoholismo(medicalRecordDto.getAlcoholismo());
    medicalRecord.setAniosFumando(medicalRecordDto.getAniosFumando());
    medicalRecord.setCigarrillosDiarios(
      medicalRecordDto.getCigarrillosDiarios()
    );
    medicalRecord.setSedentarismo(medicalRecordDto.getSedentarismo());
    medicalRecord.setEjercicioDiario(medicalRecordDto.getEjercicioDiario());
    medicalRecord.setHta(medicalRecordDto.getHta());
    medicalRecord.setAntiHipertensivos(medicalRecordDto.getAntiHipertensivos());
    medicalRecord.setDislipidemia(medicalRecordDto.getDislipidemia());
    medicalRecord.setIam(medicalRecordDto.getIam());
    medicalRecord.setEnfermedadCoronaria(
      medicalRecordDto.getEnfermedadCoronaria()
    );
    medicalRecord.setArritmia(medicalRecordDto.getArritmia());
    medicalRecord.setAneurismaAortaAbdominal(
      medicalRecordDto.getAneurismaAortaAbdominal()
    );
    medicalRecord.setEnfermedadVascularPeriferica(
      medicalRecordDto.getEnfermedadVascularPeriferica()
    );
    medicalRecord.setInsuficienciaCardiaca(
      medicalRecordDto.getInsuficienciaCardiaca()
    );
    medicalRecord.setAcvIsquemico(medicalRecordDto.getAcvIsquemico());
    medicalRecord.setAcvHemorragico(medicalRecordDto.getAcvHemorragico());
    medicalRecord.setDepresion(medicalRecordDto.getDepresion());
    medicalRecord.setDemencia(medicalRecordDto.getDemencia());
    medicalRecord.setInstitucionalizado(
      medicalRecordDto.getInstitucionalizado()
    );
    medicalRecord.setParkinson(medicalRecordDto.getParkinson());
    medicalRecord.setIrc(medicalRecordDto.getIrc());
    medicalRecord.setCirrosis(medicalRecordDto.getCirrosis());
    medicalRecord.setHda(medicalRecordDto.getHda());
    medicalRecord.setHdb(medicalRecordDto.getHdb());
    medicalRecord.setDiabetes(medicalRecordDto.getDiabetes());
    medicalRecord.setEnfermedadTiroidea(
      medicalRecordDto.getEnfermedadTiroidea()
    );
    medicalRecord.setBocio(medicalRecordDto.getBocio());
    medicalRecord.setEpoc(medicalRecordDto.getEpoc());
    medicalRecord.setAsma(medicalRecordDto.getAsma());
    medicalRecord.setTepa(medicalRecordDto.getTepa());
    medicalRecord.setDiatesisHemorragica(
      medicalRecordDto.getDiatesisHemorragica()
    );
    medicalRecord.setProstatismo(medicalRecordDto.getProstatismo());
    medicalRecord.setLitiasisRenal(medicalRecordDto.getLitiasisRenal());
    medicalRecord.setGlaucoma(medicalRecordDto.getGlaucoma());
    medicalRecord.setTombosisVenosaProfunda(
      medicalRecordDto.getTombosisVenosaProfunda()
    );
    medicalRecord.setMenarca(medicalRecordDto.getMenarca());
    medicalRecord.setEdadMenarca(medicalRecordDto.getEdadMenarca());
    medicalRecord.setMenopausia(medicalRecordDto.getMenopausia());
    medicalRecord.setEdadMenopausia(medicalRecordDto.getEdadMenopausia());
    medicalRecord.setGestas(medicalRecordDto.getGestas());
    medicalRecord.setPartos(medicalRecordDto.getPartos());
    medicalRecord.setAbortos(medicalRecordDto.getAbortos());
    medicalRecord.setOtros(medicalRecordDto.getOtros());
    medicalRecord.setCancerColon(medicalRecordDto.getCancerColon());
    medicalRecord.setCancerPulmon(medicalRecordDto.getCancerPulmon());
    medicalRecord.setCancerMama(medicalRecordDto.getCancerMama());
    medicalRecord.setCancerOvario(medicalRecordDto.getCancerOvario());
    medicalRecord.setCancerProstata(medicalRecordDto.getCancerProstata());
    medicalRecord.setCancerRinon(medicalRecordDto.getCancerRinon());
    medicalRecord.setLupusEritematosoSistemico(
      medicalRecordDto.getLupusEritematosoSistemico()
    );
    medicalRecord.setArtritisReumatoidea(
      medicalRecordDto.getArtritisReumatoidea()
    );
    medicalRecord.setVasculitis(medicalRecordDto.getVasculitis());
    medicalRecord.setEnfermedadesAutoinmunes(
      medicalRecordDto.getEnfermedadesAutoinmunes()
    );
    medicalRecord.setTratamientoCronicoConCorticoides(
      medicalRecordDto.getTratamientoCronicoConCorticoides()
    );
    medicalRecord.setPenicilina(medicalRecordDto.getPenicilina());
    medicalRecord.setIodo(medicalRecordDto.getIodo());
    medicalRecord.setDipirona(medicalRecordDto.getDipirona());
    medicalRecord.setAspirina(medicalRecordDto.getAspirina());
    medicalRecord.setOtrasAlergias(medicalRecordDto.getOtrasAlergias());
    medicalRecord.setAngioplastiaCoronaria(
      medicalRecordDto.getAngioplastiaCoronaria()
    );
    medicalRecord.setIntervencionBypassCoronario(
      medicalRecordDto.getIntervencionBypassCoronario()
    );
    medicalRecord.setEsplenectomia(medicalRecordDto.getEsplenectomia());
    medicalRecord.setColecistectomiaClasica(
      medicalRecordDto.getColecistectomiaClasica()
    );
    medicalRecord.setColecistectomiaPorVideo(
      medicalRecordDto.getColecistectomiaPorVideo()
    );
    medicalRecord.setApendicectomia(medicalRecordDto.getApendicectomia());
    medicalRecord.setHomoplastiaInguinal(
      medicalRecordDto.getHomoplastiaInguinal()
    );
    medicalRecord.setAmigdalectomia(medicalRecordDto.getAmigdalectomia());
    medicalRecord.setHemorroidectomia(medicalRecordDto.getHemorroidectomia());
    medicalRecord.setCesarea(medicalRecordDto.getCesarea());
    medicalRecord.setHisterectomia(medicalRecordDto.getHisterectomia());
    medicalRecord.setTransplantes(medicalRecordDto.getTransplantes());
    medicalRecord.setOtrosProcedimientos(
      medicalRecordDto.getOtrosProcedimientos()
    );

    patientService.save(patient);
    return new ResponseEntity<>(
      new Message("Medical record updated."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "new-evolution")
  public ResponseEntity<?> newEvolution(
    @RequestBody EvolutionDto evolutionDto,
    Authentication authentication
  ) {
    if (!doctorService.existsByUsername(authentication.getName())) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !medicalRecordService.existsById(
        UUID.fromString(evolutionDto.getMedicalRecordId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("Medical record not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !appointmentService.existsById(
        UUID.fromString(evolutionDto.getAppointmentId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("Appointment not found."),
        HttpStatus.NOT_FOUND
      );
    }

    MedicalRecord medicalRecord = medicalRecordService
      .getById(UUID.fromString(evolutionDto.getMedicalRecordId()))
      .get();
    Evolution evolution = new Evolution();
    Doctor doctor = doctorService.getByUsername(authentication.getName()).get();

    evolution.setDescription(evolutionDto.getDescription());
    evolution.setDoctorId(doctor.getId());
    evolution.setDoctorName(doctor.getLastname() + ", " + doctor.getName());
    evolution.setDate(ZonedDateTime.now());
    evolutionService.save(evolution);
    evolution.setMedicalRecord(medicalRecord);
    medicalRecord.addEvolution(evolution);
    medicalRecordService.save(medicalRecord);
    evolutionService.save(evolution);

    Appointment appointment = appointmentService
      .getById(UUID.fromString(evolutionDto.getAppointmentId()))
      .get();
    appointment.setStatus(AppointmentStatus.COMPLETED);
    appointmentService.save(appointment);

    return new ResponseEntity<>(
      new Message("Evolution created."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "update-evolution")
  public ResponseEntity<?> updateEvolution(
    @RequestBody EvolutionDto evolutionDto,
    @RequestParam String id
  ) {
    if (!evolutionService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("Evolution not found."),
        HttpStatus.NOT_FOUND
      );
    }
    Evolution evolution = evolutionService.getById(UUID.fromString(id)).get();
    evolution.setDescription(evolutionDto.getDescription());
    evolutionService.save(evolution);

    return new ResponseEntity<>(
      new Message("Evolution updated."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "new-ultrasound-report")
  public ResponseEntity<?> newUltrasoundReport(
    @RequestBody UltrasoundStudyReportDto ultrasoundStudyReportDto,
    Authentication authentication
  ) {
    if (!ultrasoundDoctorService.existsByUsername(authentication.getName())) {
      return new ResponseEntity<>(
        new Message("Doctor not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !medicalRecordService.existsById(
        UUID.fromString(ultrasoundStudyReportDto.getMedicalRecordId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("Medical record not found."),
        HttpStatus.NOT_FOUND
      );
    }
    if (
      !ultrasoundAppointmentService.existsById(
        UUID.fromString(ultrasoundStudyReportDto.getAppointmentId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("Appointment not found."),
        HttpStatus.NOT_FOUND
      );
    }

    MedicalRecord medicalRecord = medicalRecordService
      .getById(UUID.fromString(ultrasoundStudyReportDto.getMedicalRecordId()))
      .get();
    UltrasoundStudyReport ultrasoundStudyReport = new UltrasoundStudyReport();
    UltrasoundDoctor ultrasoundDoctor = ultrasoundDoctorService
      .getByUsername(authentication.getName())
      .get();

    ultrasoundStudyReport.setTitle(ultrasoundStudyReportDto.getTitle());
    ultrasoundStudyReport.setDescription(
      ultrasoundStudyReportDto.getDescription()
    );
    ultrasoundStudyReport.setDoctorId(ultrasoundDoctor.getId());
    ultrasoundStudyReport.setDoctorName(
      ultrasoundDoctor.getLastname() + ", " + ultrasoundDoctor.getName()
    );
    ultrasoundStudyReport.setDate(ZonedDateTime.now());
    ultrasoundStudyReportService.save(ultrasoundStudyReport);
    ultrasoundStudyReport.setMedicalRecord(medicalRecord);
    medicalRecord.addUltrasoundStudyReport(ultrasoundStudyReport);
    medicalRecordService.save(medicalRecord);
    ultrasoundStudyReportService.save(ultrasoundStudyReport);

    UltrasoundAppointment ultrasoundAppointment = ultrasoundAppointmentService
      .getById(UUID.fromString(ultrasoundStudyReportDto.getAppointmentId()))
      .get();
    ultrasoundAppointment.setStatus(AppointmentStatus.COMPLETED);
    ultrasoundAppointmentService.save(ultrasoundAppointment);

    return new ResponseEntity<>(
      new Message("Ultrasound Report created."),
      HttpStatus.OK
    );
  }

  @PutMapping(value = "update-ultrasound-report")
  public ResponseEntity<?> updateUltrasoundReport(
    @RequestBody UltrasoundStudyReportDto ultrasoundStudyReportDto,
    @RequestParam String id
  ) {
    if (!ultrasoundStudyReportService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("Ultrasound Report not found."),
        HttpStatus.NOT_FOUND
      );
    }
    UltrasoundStudyReport ultrasoundStudyReport = ultrasoundStudyReportService
      .getById(UUID.fromString(id))
      .get();
    ultrasoundStudyReport.setTitle(ultrasoundStudyReportDto.getTitle());
    ultrasoundStudyReport.setDescription(
      ultrasoundStudyReportDto.getDescription()
    );
    ultrasoundStudyReportService.save(ultrasoundStudyReport);

    return new ResponseEntity<>(
      new Message("Ultrasound Report updated."),
      HttpStatus.OK
    );
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!patientService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("User not found."),
        HttpStatus.NOT_FOUND
      );
    }
    patientService.deleteById(UUID.fromString(id));
    return new ResponseEntity<>(new Message("User deleted."), HttpStatus.OK);
  }
}
