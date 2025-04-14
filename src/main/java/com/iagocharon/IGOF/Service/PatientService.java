package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.PatientProjection;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.MedicalRecord;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Repository.AppointmentRepository;
import com.iagocharon.IGOF.Repository.InsuranceRepository;
import com.iagocharon.IGOF.Repository.MedicalRecordRepository;
import com.iagocharon.IGOF.Repository.PatientRepository;
import com.iagocharon.IGOF.Repository.UltrasoundAppointmentRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PatientService {

  @Autowired
  PatientRepository patientRepository;

  @Autowired
  InsuranceRepository insuranceRepository;

  @Autowired
  AppointmentRepository appointmentRepository;

  @Autowired
  UltrasoundAppointmentRepository ultrasoundAppointmentRepository;

  @Autowired
  MedicalRecordRepository medicalRecordRepository;

  public List<PatientProjection> getAll() {
    return patientRepository.findAllPatientProjections();
  }

  public Optional<Patient> getByUsername(String username) {
    return patientRepository.findByUsername(username);
  }

  public Optional<Patient> getByEmail(String email) {
    return patientRepository.findByEmail(email);
  }

  public boolean existsByEmail(String email) {
    return patientRepository.existsByEmail(email);
  }

  public boolean existsByUsername(String username) {
    return patientRepository.existsByUsername(username);
  }

  public Patient save(Patient patient) {
    return patientRepository.save(patient);
  }

  public void delete(Patient patient) {
    if (patient.getAppointments() != null) {
      {
        for (Appointment appointment : new ArrayList<>(
          patient.getAppointments()
        )) {
          patient.removeAppointment(appointment);
          appointment.setPatient(null);
          appointmentRepository.delete(appointment);
        }
      }
    }
    if (patient.getUltrasoundAppointments() != null) {
      {
        for (UltrasoundAppointment appointment : new ArrayList<>(
          patient.getUltrasoundAppointments()
        )) {
          patient.removeUltrasoundAppointment(appointment);
          appointment.setPatient(null);
          ultrasoundAppointmentRepository.delete(appointment);
        }
      }
    }
    if (patient.getMedicalRecord() != null) {
      {
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        medicalRecord.setPatient(null);
        medicalRecordRepository.delete(medicalRecord);
      }
    }
    patientRepository.delete(patient);
  }

  public void deleteById(UUID id) {
    Patient patient = patientRepository.findById(id).get();
    if (patient.getAppointments() != null) {
      {
        for (Appointment appointment : new ArrayList<>(
          patient.getAppointments()
        )) {
          patient.removeAppointment(appointment);
          appointment.setPatient(null);
          appointmentRepository.delete(appointment);
        }
      }
    }
    if (patient.getUltrasoundAppointments() != null) {
      {
        for (UltrasoundAppointment appointment : new ArrayList<>(
          patient.getUltrasoundAppointments()
        )) {
          patient.removeUltrasoundAppointment(appointment);
          appointment.setPatient(null);
          ultrasoundAppointmentRepository.delete(appointment);
        }
      }
    }
    if (patient.getMedicalRecord() != null) {
      {
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        medicalRecord.setPatient(null);
        medicalRecordRepository.delete(medicalRecord);
      }
    }
    patientRepository.deleteById(id);
  }

  public Optional<Patient> getById(UUID id) {
    return patientRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return patientRepository.existsById(id);
  }
}
