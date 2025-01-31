package com.iagocharon.IGOF.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Entity.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

  Optional<MedicalRecord> findById(UUID id);

  boolean existsById(UUID id);


}
