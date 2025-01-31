package com.iagocharon.IGOF.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Dto.Projections.AppointmentProjection;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Repository.AppointmentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppointmentService {

  @Autowired
  AppointmentRepository appointmentRepository;

  @Autowired
  DoctorService doctorService;

  @Autowired
  PatientService patientService;

  public void save(Appointment appointment) {
    appointmentRepository.save(appointment);
  }

  public void delete(Appointment appointment) {
    Doctor doctor = appointment.getDoctor();
    doctor.removeAppointment(appointment);
    doctorService.save(doctor);
    Patient patient = appointment.getPatient();
    patient.removeAppointment(appointment);
    patientService.save(patient);

    appointmentRepository.delete(appointment);
  }

  public void deleteById(UUID id) {
    Appointment appointment = appointmentRepository.findById(id).get();
    Doctor doctor = appointment.getDoctor();
    doctor.removeAppointment(appointment);
    doctorService.save(doctor);
    Patient patient = appointment.getPatient();
    patient.removeAppointment(appointment);
    patientService.save(patient);

    appointmentRepository.deleteById(id);
  }

  public Optional<Appointment> getById(UUID id) {
    return appointmentRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return appointmentRepository.existsById(id);
  }

  public List<Appointment> getAll() {
    return appointmentRepository.findAll();
  }

  public List<AppointmentProjection> findAllByDoctorAndDateRange(UUID doctorId,
      ZonedDateTime startDate, ZonedDateTime endDate) {
    return appointmentRepository.findAllByDoctorAndDateRange(doctorId, startDate, endDate);
  }

  public List<AppointmentProjection> findAllByPatientAndDateRange(UUID patientId,
      ZonedDateTime startDate, ZonedDateTime endDate) {
    return appointmentRepository.findAllByPatientAndDateRange(patientId, startDate, endDate);
  }



}
