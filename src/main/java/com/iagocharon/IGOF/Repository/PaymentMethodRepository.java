package com.iagocharon.IGOF.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Entity.PaymentMethod;


@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

  boolean existsById(UUID id);

  Optional<PaymentMethod> findById(UUID id);

  boolean existsByName(String name);

  Optional<PaymentMethod> findByName(String name);



}
