package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundStudyStatus;
import com.iagocharon.IGOF.Repository.UltrasoundAppointmentRepository;
import com.iagocharon.IGOF.Repository.UltrasoundStudyStatusRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UltrasoundStudyStatusService {

  @Autowired
  UltrasoundStudyStatusRepository ultrasoundStudyStatusRepository;

  @Autowired
  UltrasoundAppointmentRepository ultrasoundAppointmentRepository;

  public Optional<UltrasoundStudyStatus> findByName(String name) {
    return ultrasoundStudyStatusRepository.findByName(name);
  }

  public boolean existsByName(String name) {
    return ultrasoundStudyStatusRepository.existsByName(name);
  }

  public boolean existsById(UUID id) {
    return ultrasoundStudyStatusRepository.existsById(id);
  }

  public Optional<UltrasoundStudyStatus> findById(UUID id) {
    return ultrasoundStudyStatusRepository.findById(id);
  }

  public void save(UltrasoundStudyStatus ultrasoundStudyStatus) {
    ultrasoundStudyStatusRepository.save(ultrasoundStudyStatus);
  }

  public void delete(UltrasoundStudyStatus ultrasoundStudyStatus) {
    UltrasoundAppointment ultrasoundAppointment =
      ultrasoundStudyStatus.getUltrasoundAppointment();
    ultrasoundAppointment.removeUltrasoundStudyStatus(ultrasoundStudyStatus);
    ultrasoundAppointmentRepository.save(ultrasoundAppointment);
    ultrasoundStudyStatusRepository.delete(ultrasoundStudyStatus);
  }

  public void deleteById(UUID id) {
    UltrasoundStudyStatus ultrasoundStudyStatus =
      ultrasoundStudyStatusRepository.findById(id).get();
    UltrasoundAppointment ultrasoundAppointment =
      ultrasoundStudyStatus.getUltrasoundAppointment();
    ultrasoundAppointment.removeUltrasoundStudyStatus(ultrasoundStudyStatus);
    ultrasoundAppointmentRepository.save(ultrasoundAppointment);
    ultrasoundStudyStatusRepository.deleteById(id);
  }
}
