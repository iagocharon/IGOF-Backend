package com.iagocharon.IGOF.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Entity.PaymentMethod;
import com.iagocharon.IGOF.Repository.PaymentMethodRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentMethodService {

  @Autowired
  PaymentMethodRepository paymentMethodRepository;

  public List<PaymentMethod> getAll() {
    return paymentMethodRepository.findAll();
  }

  public boolean existsById(UUID id) {
    return paymentMethodRepository.existsById(id);
  }

  public Optional<PaymentMethod> getById(UUID id) {
    return paymentMethodRepository.findById(id);
  }

  public boolean existsByName(String name) {
    return paymentMethodRepository.existsByName(name);
  }

  public Optional<PaymentMethod> getByName(String name) {
    return paymentMethodRepository.findByName(name);
  }

  public PaymentMethod save(PaymentMethod paymentMethod) {
    return paymentMethodRepository.save(paymentMethod);
  }

  public void delete(PaymentMethod paymentMethod) {
    paymentMethodRepository.delete(paymentMethod);
  }

}
