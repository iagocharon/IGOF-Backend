package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.UltrasoundDoctorProjection;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Repository.InsuranceRepository;
import com.iagocharon.IGOF.Repository.UltrasoundAppointmentRepository;
import com.iagocharon.IGOF.Repository.UltrasoundDoctorRepository;
import com.iagocharon.IGOF.Repository.UltrasoundStudyRepository;
import com.iagocharon.IGOF.Repository.WorkScheduleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UltrasoundDoctorService {

  @Autowired
  UltrasoundDoctorRepository ultrasoundDoctorRepository;

  @Autowired
  InsuranceRepository insuranceRepository;

  @Autowired
  UltrasoundAppointmentRepository ultrasoundAppointmentRepository;

  @Autowired
  WorkScheduleRepository workScheduleRepository;

  @Autowired
  UltrasoundStudyRepository ultrasoundStudyRepository;

  public Optional<UltrasoundDoctor> getByUsername(String username) {
    return ultrasoundDoctorRepository.findByUsername(username);
  }

  public Optional<UltrasoundDoctor> getById(UUID id) {
    return ultrasoundDoctorRepository.findById(id);
  }

  public boolean existsByUsername(String username) {
    return ultrasoundDoctorRepository.existsByUsername(username);
  }

  public boolean existsById(UUID id) {
    return ultrasoundDoctorRepository.existsById(id);
  }

  public boolean existsByEmail(String email) {
    return ultrasoundDoctorRepository.existsByEmail(email);
  }

  public List<UltrasoundDoctorProjection> findAllProjectedBy() {
    return ultrasoundDoctorRepository.findAllProjectedBy();
  }

  public List<
    UltrasoundDoctorProjection
  > findAllProjectedByStudyIdAndInsuranceId(
    UUID ultrasoundStudyId,
    UUID insuranceId
  ) {
    return ultrasoundDoctorRepository.findAllProjectedByStudyIdAndInsuranceId(
      ultrasoundStudyId,
      insuranceId
    );
  }

  public List<UltrasoundDoctorProjection> findAllProjectedByUltrasoundStudiesId(
    UUID id
  ) {
    return ultrasoundDoctorRepository.findAllProjectedByUltrasoundStudiesId(id);
  }

  public UltrasoundDoctor save(UltrasoundDoctor doctor) {
    return ultrasoundDoctorRepository.save(doctor);
  }

  public void delete(UltrasoundDoctor doctor) {
    List<Insurance> insurances = doctor.getInsurances();
    for (Insurance insurance : insurances) {
      insurance.removeUltrasoundDoctor(doctor);
      insuranceRepository.save(insurance);
    }

    List<UltrasoundAppointment> appointments =
      doctor.getUltrasoundAppointments();
    for (UltrasoundAppointment appointment : appointments) {
      appointment.setUltrasoundDoctor(null);
      ultrasoundAppointmentRepository.save(appointment);
    }

    List<UltrasoundStudy> ultrasoundStudies = doctor.getUltrasoundStudies();
    for (UltrasoundStudy ultrasoundStudy : ultrasoundStudies) {
      ultrasoundStudy.removeUltrasoundDoctor(doctor);
      ultrasoundStudyRepository.save(ultrasoundStudy);
    }

    List<WorkSchedule> workSchedules = doctor.getWorkSchedules();
    for (WorkSchedule workSchedule : workSchedules) {
      workSchedule.setUltrasoundDoctor(null);
      workScheduleRepository.save(workSchedule);
      workScheduleRepository.delete(workSchedule);
    }

    ultrasoundDoctorRepository.delete(doctor);
  }

  public void deleteById(UUID id) {
    UltrasoundDoctor doctor = ultrasoundDoctorRepository.findById(id).get();

    List<Insurance> insurances = doctor.getInsurances();
    for (Insurance insurance : insurances) {
      insurance.removeUltrasoundDoctor(doctor);
      insuranceRepository.save(insurance);
    }

    List<UltrasoundAppointment> appointments =
      doctor.getUltrasoundAppointments();
    for (UltrasoundAppointment appointment : appointments) {
      appointment.setUltrasoundDoctor(null);
      ultrasoundAppointmentRepository.save(appointment);
    }

    List<UltrasoundStudy> ultrasoundStudies = doctor.getUltrasoundStudies();
    for (UltrasoundStudy ultrasoundStudy : ultrasoundStudies) {
      ultrasoundStudy.removeUltrasoundDoctor(doctor);
      ultrasoundStudyRepository.save(ultrasoundStudy);
    }

    List<WorkSchedule> workSchedules = doctor.getWorkSchedules();
    for (WorkSchedule workSchedule : workSchedules) {
      workSchedule.setUltrasoundDoctor(null);
      workScheduleRepository.save(workSchedule);
      workScheduleRepository.delete(workSchedule);
    }

    ultrasoundDoctorRepository.deleteById(id);
  }
}
