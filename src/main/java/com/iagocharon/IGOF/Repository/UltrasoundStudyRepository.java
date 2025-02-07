package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Dto.Projections.UltrasoundStudyProjection;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UltrasoundStudyRepository
  extends JpaRepository<UltrasoundStudy, UUID> {
  Optional<UltrasoundStudy> findByName(String name);
  boolean existsByName(String name);
  boolean existsById(UUID id);
  Optional<UltrasoundStudy> findById(UUID id);

  @Query("SELECT u.id AS id, u.name AS name FROM UltrasoundStudy u")
  List<UltrasoundStudyProjection> findAllProjectedBy();
}
