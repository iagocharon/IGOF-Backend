package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Entity.UltrasoundStudyReport;
import com.iagocharon.IGOF.Repository.UltrasoundStudyReportRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UltrasoundStudyReportService {

  @Autowired
  UltrasoundStudyReportRepository ultrasoundStudyReportRepository;

  public void deleteById(UUID id) {
    ultrasoundStudyReportRepository.deleteById(id);
  }

  public boolean existsById(UUID id) {
    return ultrasoundStudyReportRepository.existsById(id);
  }

  public Optional<UltrasoundStudyReport> getById(UUID id) {
    return ultrasoundStudyReportRepository.findById(id);
  }

  public UltrasoundStudyReport save(
    UltrasoundStudyReport ultrasoundStudyReport
  ) {
    return ultrasoundStudyReportRepository.save(ultrasoundStudyReport);
  }

  public List<UltrasoundStudyReport> getAll() {
    return ultrasoundStudyReportRepository.findAllByOrderByDateDesc();
  }
}
