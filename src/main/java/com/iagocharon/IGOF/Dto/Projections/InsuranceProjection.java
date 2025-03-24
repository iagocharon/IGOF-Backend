package com.iagocharon.IGOF.Dto.Projections;

import java.util.UUID;

public interface InsuranceProjection {
  public UUID getId();

  public String getName();

  public InsuranceParentProjection getInsuranceParent();
}
