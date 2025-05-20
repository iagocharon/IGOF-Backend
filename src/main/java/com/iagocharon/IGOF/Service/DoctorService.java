package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Repository.AppointmentRepository;
import com.iagocharon.IGOF.Repository.DoctorRepository;
import com.iagocharon.IGOF.Repository.InsuranceRepository;
import com.iagocharon.IGOF.Repository.WorkScheduleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DoctorService {

  @Autowired
  InsuranceRepository insuranceRepository;

  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  WorkScheduleRepository workScheduleRepository;

  @Autowired
  AppointmentRepository appointmentRepository;

  @Autowired
  SpecialtyService specialtyService;

  public List<DoctorProjection> getAll() {
    return doctorRepository.findAllProjectedBy();
  }

  public List<Doctor> getAllDoctors() {
    return doctorRepository.findAll();
  }

  public List<DoctorProjection> getAllBySpecialtyAndInsuranceId(
    UUID specialtyId,
    UUID insuranceId
  ) {
    return doctorRepository.findAllProjectedBySpecialtyIdAndInsuranceId(
      specialtyId,
      insuranceId
    );
  }

  public Doctor save(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  public void delete(Doctor doctor) {
    List<Insurance> insurances = doctor.getInsurances();
    for (Insurance insurance : insurances) {
      insurance.removeDoctor(doctor);
      insuranceRepository.save(insurance);
    }

    List<WorkSchedule> workSchedules = doctor.getWorkSchedules();
    for (WorkSchedule workSchedule : workSchedules) {
      workSchedule.setDoctor(null);
      workScheduleRepository.save(workSchedule);
      workScheduleRepository.delete(workSchedule);
    }

    List<Appointment> appointments = doctor.getAppointments();
    for (Appointment appointment : appointments) {
      appointment.setDoctor(null);
      appointmentRepository.save(appointment);
    }

    List<Specialty> specialties = doctor.getSpecialties();
    for (Specialty specialty : specialties) {
      specialty.removeDoctor(doctor);
      specialtyService.save(specialty);
    }

    doctorRepository.delete(doctor);
  }

  public void deleteById(UUID id) {
    Doctor doctor = doctorRepository.findById(id).get();
    List<Insurance> insurances = doctor.getInsurances();
    for (Insurance insurance : insurances) {
      insurance.removeDoctor(doctor);
      insuranceRepository.save(insurance);
    }

    List<WorkSchedule> workSchedules = doctor.getWorkSchedules();
    for (WorkSchedule workSchedule : workSchedules) {
      workSchedule.setDoctor(null);
      workScheduleRepository.save(workSchedule);
      workScheduleRepository.delete(workSchedule);
    }

    List<Appointment> appointments = doctor.getAppointments();
    for (Appointment appointment : appointments) {
      appointment.setDoctor(null);
      appointmentRepository.save(appointment);
    }

    List<Specialty> specialties = doctor.getSpecialties();
    for (Specialty specialty : specialties) {
      specialty.removeDoctor(doctor);
      specialtyService.save(specialty);
    }

    doctorRepository.deleteById(id);
  }

  public Optional<Doctor> getById(UUID id) {
    return doctorRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return doctorRepository.existsById(id);
  }

  public Optional<Doctor> getByUsername(String username) {
    return doctorRepository.findByUsername(username);
  }

  public boolean existsByUsername(String username) {
    return doctorRepository.existsByUsername(username);
  }
}
