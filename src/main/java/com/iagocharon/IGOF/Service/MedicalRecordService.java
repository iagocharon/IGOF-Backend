package com.iagocharon.IGOF.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Entity.MedicalRecord;
import com.iagocharon.IGOF.Repository.MedicalRecordRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicalRecordService {

  @Autowired
  MedicalRecordRepository medicalRecordRepository;

  public void deleteById(UUID id) {
    medicalRecordRepository.deleteById(id);
  }

  public boolean existsById(UUID id) {
    return medicalRecordRepository.existsById(id);
  }

  public Optional<MedicalRecord> getById(UUID id) {
    return medicalRecordRepository.findById(id);
  }

  public MedicalRecord save(MedicalRecord medicalRecord) {
    return medicalRecordRepository.save(medicalRecord);
  }



}
