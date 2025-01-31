package com.iagocharon.IGOF.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Dto.Projections.PatientProjection;
import com.iagocharon.IGOF.Entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
  boolean existsById(UUID id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<Patient> findByUsername(String username);

  Optional<Patient> findById(UUID id);

  Optional<Patient> findByEmail(String email);

  @Query(
    "SELECT p.id AS id, p.username AS username, p.email AS email, p.name AS name, p.lastname AS lastname, p.phone AS phone, p.birthdate AS birthdate FROM Patient p"
  )
  List<PatientProjection> findAllPatientProjections();
}
