package com.iagocharon.IGOF.Dto.Projections;

import java.util.UUID;

public interface InsuranceParentProjection {
  UUID getId();

  public String getName();

  public int getDailyLimit();
}
