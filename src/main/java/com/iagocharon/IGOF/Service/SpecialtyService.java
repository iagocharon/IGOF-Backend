package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Dto.Projections.SpecialtyProjection;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Repository.DoctorRepository;
import com.iagocharon.IGOF.Repository.SpecialtyRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SpecialtyService {

  @Autowired
  SpecialtyRepository specialtyRepository;

  @Autowired
  DoctorRepository doctorRepository;

  public List<SpecialtyProjection> getAll() {
    return specialtyRepository.findAllProjectedBy();
  }

  public boolean existsById(UUID id) {
    return specialtyRepository.existsById(id);
  }

  public boolean existsByName(String name) {
    return specialtyRepository.existsByName(name);
  }

  public Optional<Specialty> getByName(String name) {
    return specialtyRepository.findByName(name);
  }

  public Optional<Specialty> getById(UUID id) {
    return specialtyRepository.findById(id);
  }

  public Specialty save(Specialty specialty) {
    return specialtyRepository.save(specialty);
  }

  public void delete(Specialty specialty) {
    specialtyRepository.delete(specialty);
  }

  public void deleteById(UUID id) {
    specialtyRepository.deleteById(id);
  }

  public List<DoctorProjection> getDoctors(UUID id) {
    return doctorRepository.findAllProjectedBySpecialtiesId(id);
  }

  public List<Specialty> saveAll(List<Specialty> specialties) {
    return specialtyRepository.saveAll(specialties);
  }
}
