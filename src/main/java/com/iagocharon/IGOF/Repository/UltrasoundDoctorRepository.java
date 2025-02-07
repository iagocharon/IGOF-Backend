package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Dto.Projections.UltrasoundDoctorProjection;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UltrasoundDoctorRepository
  extends JpaRepository<UltrasoundDoctor, UUID> {
  Optional<UltrasoundDoctor> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsById(UUID id);
  boolean existsByEmail(String email);
  Optional<UltrasoundDoctor> findById(UUID id);

  List<UltrasoundDoctorProjection> findAllProjectedBy();

  @Query(
    """
        SELECT d FROM UltrasoundDoctor d
        JOIN d.insurances i
        JOIN d.ultrasoundStudies s
        WHERE s.id = :ultrasoundStudyId
        AND i.id = :insuranceId
    """
  )
  List<UltrasoundDoctorProjection> findAllProjectedByStudyIdAndInsuranceId(
    UUID ultrasoundStudyId,
    UUID insuranceId
  );

  public List<UltrasoundDoctorProjection> findAllProjectedByUltrasoundStudiesId(
    UUID id
  );
}
