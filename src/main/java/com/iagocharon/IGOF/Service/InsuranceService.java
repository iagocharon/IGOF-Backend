package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.InsuranceProjection;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Repository.DoctorRepository;
import com.iagocharon.IGOF.Repository.InsuranceRepository;
import com.iagocharon.IGOF.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InsuranceService {

  @Autowired
  InsuranceRepository insuranceRepository;

  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  PatientRepository patientRepository;

  public boolean existsById(UUID id) {
    return insuranceRepository.existsById(id);
  }

  public boolean existsByName(String name) {
    return insuranceRepository.existsByName(name);
  }

  public Optional<Insurance> getById(UUID id) {
    return insuranceRepository.findById(id);
  }

  public Optional<Insurance> getByName(String name) {
    return insuranceRepository.findByName(name);
  }

  public Insurance save(Insurance insurance) {
    return insuranceRepository.save(insurance);
  }

  public void delete(Insurance insurance) {
    for (Doctor doctor : insurance.getDoctors()) {
      doctor.removeInsurance(insurance);
      doctorRepository.save(doctor);
    }

    insuranceRepository.delete(insurance);
  }

  public void deleteById(UUID id) {
    Insurance insurance = insuranceRepository.findById(id).get();
    for (Doctor doctor : insurance.getDoctors()) {
      doctor.removeInsurance(insurance);
      doctorRepository.save(doctor);
    }
    insuranceRepository.deleteById(id);
  }

  public List<InsuranceProjection> getAll() {
    return insuranceRepository.findAllProjectedBy();
  }

  public List<Insurance> getAllInsurances() {
    return insuranceRepository.findAll();
  }

  public List<Insurance> saveAll(List<Insurance> insurances) {
    return insuranceRepository.saveAll(insurances);
  }
}
