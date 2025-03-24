package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Dto.Projections.InsuranceParentProjection;
import com.iagocharon.IGOF.Entity.InsuranceParent;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceParentRepository
  extends JpaRepository<InsuranceParent, UUID> {
  boolean existsById(UUID id);
  boolean existsByName(String name);
  Optional<InsuranceParent> findById(UUID id);
  Optional<InsuranceParent> findByName(String name);
  List<InsuranceParent> findAll();
  List<InsuranceParentProjection> findAllProjectedBy();
}
