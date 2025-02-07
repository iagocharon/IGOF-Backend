package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Entity.UltrasoundStudyReport;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UltrasoundStudyReportRepository
  extends JpaRepository<UltrasoundStudyReport, UUID> {
  Optional<UltrasoundStudyReport> findById(UUID id);

  boolean existsById(UUID id);
}
