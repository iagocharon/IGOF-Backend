package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Entity.Doctor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
  boolean existsById(UUID id);

  boolean existsByUsername(String username);

  Optional<Doctor> findByUsername(String username);

  Optional<Doctor> findById(UUID id);

  Optional<Doctor> findByEmail(String email);

  List<DoctorProjection> findAllProjectedBy();

  @Query(
    """
        SELECT d FROM Doctor d
        JOIN d.insurances i
        JOIN d.specialties s
        WHERE s.id = :specialtyId
        AND i.id = :insuranceId
    """
  )
  List<DoctorProjection> findAllProjectedBySpecialtyIdAndInsuranceId(
    UUID specialtyId,
    UUID insuranceId
  );

  public List<DoctorProjection> findAllProjectedBySpecialtiesId(UUID id);
}
