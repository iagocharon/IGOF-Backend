package com.iagocharon.IGOF.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Entity.Evolution;

@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, UUID> {

  Optional<Evolution> findById(UUID id);

  boolean existsById(UUID id);

}
