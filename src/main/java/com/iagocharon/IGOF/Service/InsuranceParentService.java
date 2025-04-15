package com.iagocharon.IGOF.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Dto.Projections.InsuranceParentProjection;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.InsuranceParent;
import com.iagocharon.IGOF.Repository.InsuranceParentRepository;
import com.iagocharon.IGOF.Repository.InsuranceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InsuranceParentService {

  @Autowired
  InsuranceParentRepository insuranceParentRepository;

  @Autowired
  InsuranceRepository insuranceRepository;

  public boolean existsById(UUID id) {
    return insuranceParentRepository.existsById(id);
  }

  public boolean existsByName(String name) {
    return insuranceParentRepository.existsByName(name);
  }

  public Optional<InsuranceParent> getById(UUID id) {
    return insuranceParentRepository.findById(id);
  }

  public Optional<InsuranceParent> getByName(String name) {
    return insuranceParentRepository.findByName(name);
  }

  public InsuranceParent save(InsuranceParent insuranceParent) {
    return insuranceParentRepository.save(insuranceParent);
  }

  public void delete(InsuranceParent insuranceParent) {
    List<Insurance> insurances = insuranceParent.getInsurances();
    for (Insurance insurance : insurances) {
      insuranceRepository.delete(insurance);
    }
    insuranceParentRepository.delete(insuranceParent);
  }

  public void deleteById(UUID id) {
    InsuranceParent insuranceParent = insuranceParentRepository
      .findById(id)
      .get();
    List<Insurance> insurances = insuranceParent.getInsurances();
    for (Insurance insurance : insurances) {
      insuranceRepository.delete(insurance);
    }

    delete(insuranceParent);
  }

  public List<InsuranceParentProjection> getAll() {
    return insuranceParentRepository.findAllProjectedBy();
  }

  public List<InsuranceParent> getAllInsuranceParents() {
    return insuranceParentRepository.findAll();
  }
}
