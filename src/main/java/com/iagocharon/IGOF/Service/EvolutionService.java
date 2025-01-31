package com.iagocharon.IGOF.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Entity.Evolution;
import com.iagocharon.IGOF.Repository.EvolutionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EvolutionService {

  @Autowired
  EvolutionRepository evolutionRepository;

  public void deleteById(UUID id) {
    evolutionRepository.deleteById(id);
  }

  public boolean existsById(UUID id) {
    return evolutionRepository.existsById(id);
  }

  public Optional<Evolution> getById(UUID id) {
    return evolutionRepository.findById(id);
  }

  public Evolution save(Evolution evolution) {
    return evolutionRepository.save(evolution);
  }

  public List<Evolution> getAll() {
    return evolutionRepository.findAll();
  }
}
