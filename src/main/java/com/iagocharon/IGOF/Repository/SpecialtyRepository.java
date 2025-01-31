package com.iagocharon.IGOF.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Dto.Projections.SpecialtyProjection;
import com.iagocharon.IGOF.Entity.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, UUID> {

  boolean existsById(UUID id);

  boolean existsByName(String name);

  Optional<Specialty> findByName(String name);

  Optional<Specialty> findById(UUID id);

  List<SpecialtyProjection> findAllProjectedBy();

}
