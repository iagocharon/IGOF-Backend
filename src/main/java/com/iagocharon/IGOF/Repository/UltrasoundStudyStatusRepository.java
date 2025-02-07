package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Entity.UltrasoundStudyStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UltrasoundStudyStatusRepository
  extends JpaRepository<UltrasoundStudyStatus, UUID> {
  Optional<UltrasoundStudyStatus> findByName(String name);
  boolean existsByName(String name);
  boolean existsById(UUID id);
  Optional<UltrasoundStudyStatus> findById(UUID id);

  List<UltrasoundStudyStatus> findAll();
}
