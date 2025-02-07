package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.UltrasoundAppointmentProjection;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.PaymentMethod;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudyStatus;
import com.iagocharon.IGOF.Repository.PaymentMethodRepository;
import com.iagocharon.IGOF.Repository.UltrasoundAppointmentRepository;
import com.iagocharon.IGOF.Repository.UltrasoundDoctorRepository;
import com.iagocharon.IGOF.Repository.UltrasoundStudyStatusRepository;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UltrasoundAppointmentService {

  @Autowired
  UltrasoundAppointmentRepository ultrasoundAppointmentRepository;

  @Autowired
  UltrasoundDoctorRepository ultrasoundDoctorRepository;

  @Autowired
  PatientService patientService;

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  PaymentMethodRepository paymentMethodRepository;

  @Autowired
  UltrasoundStudyStatusRepository ultrasoundStudyStatusRepository;

  public Optional<UltrasoundAppointment> getById(UUID id) {
    return ultrasoundAppointmentRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return ultrasoundAppointmentRepository.existsById(id);
  }

  public List<UltrasoundAppointment> getAll() {
    return ultrasoundAppointmentRepository.findAll();
  }

  public List<UltrasoundAppointmentProjection> findAllByDoctorAndDateRange(
    UUID doctorId,
    ZonedDateTime startDate,
    ZonedDateTime endDate
  ) {
    return ultrasoundAppointmentRepository.findAllByDoctorAndDateRange(
      doctorId,
      startDate,
      endDate
    );
  }

  public List<UltrasoundAppointmentProjection> findAllByPatientAndDateRange(
    UUID patientId,
    ZonedDateTime startDate,
    ZonedDateTime endDate
  ) {
    return ultrasoundAppointmentRepository.findAllByPatientAndDateRange(
      patientId,
      startDate,
      endDate
    );
  }

  public void save(UltrasoundAppointment ultrasoundAppointment) {
    ultrasoundAppointmentRepository.save(ultrasoundAppointment);
  }

  public void delete(UltrasoundAppointment ultrasoundAppointment) {
    UltrasoundDoctor doctor = ultrasoundAppointment.getUltrasoundDoctor();
    doctor.removeUltrasoundAppointment(ultrasoundAppointment);
    ultrasoundDoctorRepository.save(doctor);

    Patient patient = ultrasoundAppointment.getPatient();
    patient.removeUltrasoundAppointment(ultrasoundAppointment);
    patientService.save(patient);

    Insurance insurance = ultrasoundAppointment.getInsurance();
    insurance.removeUltrasoundAppointment(ultrasoundAppointment);
    insuranceService.save(insurance);

    PaymentMethod paymentMethod = ultrasoundAppointment.getPaymentMethod();
    paymentMethod.removeUltrasoundAppointment(ultrasoundAppointment);
    paymentMethodRepository.save(paymentMethod);

    List<UltrasoundStudyStatus> ultrasoundStudyStatuses =
      ultrasoundAppointment.getUltrasoundStudyStatuses();
    for (UltrasoundStudyStatus ultrasoundStudyStatus : ultrasoundStudyStatuses) {
      ultrasoundStudyStatusRepository.delete(ultrasoundStudyStatus);
    }

    ultrasoundAppointmentRepository.delete(ultrasoundAppointment);
  }

  public void deleteById(UUID id) {
    UltrasoundAppointment ultrasoundAppointment =
      ultrasoundAppointmentRepository.findById(id).get();

    UltrasoundDoctor doctor = ultrasoundAppointment.getUltrasoundDoctor();
    doctor.removeUltrasoundAppointment(ultrasoundAppointment);
    ultrasoundDoctorRepository.save(doctor);

    Patient patient = ultrasoundAppointment.getPatient();
    patient.removeUltrasoundAppointment(ultrasoundAppointment);
    patientService.save(patient);

    Insurance insurance = ultrasoundAppointment.getInsurance();
    insurance.removeUltrasoundAppointment(ultrasoundAppointment);
    insuranceService.save(insurance);

    PaymentMethod paymentMethod = ultrasoundAppointment.getPaymentMethod();
    paymentMethod.removeUltrasoundAppointment(ultrasoundAppointment);
    paymentMethodRepository.save(paymentMethod);

    List<UltrasoundStudyStatus> ultrasoundStudyStatuses =
      ultrasoundAppointment.getUltrasoundStudyStatuses();
    for (UltrasoundStudyStatus ultrasoundStudyStatus : ultrasoundStudyStatuses) {
      ultrasoundStudyStatusRepository.delete(ultrasoundStudyStatus);
    }

    ultrasoundAppointmentRepository.deleteById(id);
  }

  public void clearStatuses(UltrasoundAppointment ultrasoundAppointment) {
    List<UltrasoundStudyStatus> ultrasoundStudyStatuses =
      ultrasoundAppointment.getUltrasoundStudyStatuses();
    for (UltrasoundStudyStatus ultrasoundStudyStatus : ultrasoundStudyStatuses) {
      ultrasoundStudyStatusRepository.delete(ultrasoundStudyStatus);
    }
  }

  public void saveWithStatuses(
    UltrasoundAppointment ultrasoundAppointment,
    List<UltrasoundStudyStatus> ultrasoundStudyStatuses
  ) {
    ultrasoundAppointmentRepository.save(ultrasoundAppointment);
    ultrasoundAppointment.setUltrasoundStudyStatuses(ultrasoundStudyStatuses);
    for (UltrasoundStudyStatus ultrasoundStudyStatus : ultrasoundAppointment.getUltrasoundStudyStatuses()) {
      ultrasoundStudyStatusRepository.save(ultrasoundStudyStatus);
    }
    ultrasoundAppointmentRepository.save(ultrasoundAppointment);
  }
}
