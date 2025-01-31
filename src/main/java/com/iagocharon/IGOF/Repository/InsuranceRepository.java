package com.iagocharon.IGOF.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Entity.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, UUID> {
  boolean existsById(UUID id);

  boolean existsByName(String name);

  Optional<Insurance> findById(UUID id);

  Optional<Insurance> findByName(String name);
}
