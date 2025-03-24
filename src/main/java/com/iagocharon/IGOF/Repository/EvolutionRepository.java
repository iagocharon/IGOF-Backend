package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Entity.Evolution;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, UUID> {
  Optional<Evolution> findById(UUID id);

  boolean existsById(UUID id);

  List<Evolution> findAllByOrderByDateDesc();
}
