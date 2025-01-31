package com.iagocharon.IGOF.Dto.Projections;

import java.time.LocalDate;
import java.util.UUID;

public interface PatientProjection {
  public UUID getId();

  public String getUsername();

  public String getEmail();

  public String getName();

  public String getLastname();

  public String getPhone();

  public LocalDate getBirthdate();
}
