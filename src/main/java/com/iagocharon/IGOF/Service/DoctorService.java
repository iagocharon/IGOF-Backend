package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Repository.DoctorRepository;
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
  DoctorRepository doctorRepository;

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
    doctorRepository.delete(doctor);
  }

  public void deleteById(UUID id) {
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
