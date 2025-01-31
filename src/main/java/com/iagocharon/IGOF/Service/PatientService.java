package com.iagocharon.IGOF.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Dto.Projections.PatientProjection;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Repository.InsuranceRepository;
import com.iagocharon.IGOF.Repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientService {

  @Autowired
  PatientRepository patientRepository;

  @Autowired
  InsuranceRepository insuranceRepository;

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
    patientRepository.delete(patient);
  }

  public void deleteById(UUID id) {
    patientRepository.deleteById(id);
  }

  public Optional<Patient> getById(UUID id) {
    return patientRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return patientRepository.existsById(id);
  }
}
