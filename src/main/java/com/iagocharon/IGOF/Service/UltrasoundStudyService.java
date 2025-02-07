package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.UltrasoundDoctorProjection;
import com.iagocharon.IGOF.Dto.Projections.UltrasoundStudyProjection;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import com.iagocharon.IGOF.Repository.UltrasoundDoctorRepository;
import com.iagocharon.IGOF.Repository.UltrasoundStudyRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UltrasoundStudyService {

  @Autowired
  UltrasoundStudyRepository ultrasoundStudyRepository;

  @Autowired
  UltrasoundDoctorRepository ultrasoundDoctorRepository;

  public Optional<UltrasoundStudy> getById(UUID id) {
    return ultrasoundStudyRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return ultrasoundStudyRepository.existsById(id);
  }

  public boolean existsByName(String name) {
    return ultrasoundStudyRepository.existsByName(name);
  }

  public UltrasoundStudy save(UltrasoundStudy ultrasoundStudy) {
    return ultrasoundStudyRepository.save(ultrasoundStudy);
  }

  public void delete(UltrasoundStudy ultrasoundStudy) {
    List<UltrasoundDoctor> doctors = ultrasoundStudy.getUltrasoundDoctors();
    for (UltrasoundDoctor doctor : doctors) {
      doctor.removeUltrasoundStudy(ultrasoundStudy);
      ultrasoundDoctorRepository.save(doctor);
    }

    ultrasoundStudyRepository.delete(ultrasoundStudy);
  }

  public void deleteById(UUID id) {
    UltrasoundStudy ultrasoundStudy = ultrasoundStudyRepository
      .findById(id)
      .get();
    List<UltrasoundDoctor> doctors = ultrasoundStudy.getUltrasoundDoctors();
    for (UltrasoundDoctor doctor : doctors) {
      doctor.removeUltrasoundStudy(ultrasoundStudy);
      ultrasoundDoctorRepository.save(doctor);
    }
    ultrasoundStudyRepository.deleteById(id);
  }

  public void saveAll(List<UltrasoundStudy> ultrasoundStudies) {
    ultrasoundStudyRepository.saveAll(ultrasoundStudies);
  }

  public List<UltrasoundStudyProjection> getAll() {
    return ultrasoundStudyRepository.findAllProjectedBy();
  }

  public List<UltrasoundDoctorProjection> getDoctors(UUID id) {
    return ultrasoundDoctorRepository.findAllProjectedByUltrasoundStudiesId(id);
  }
}
