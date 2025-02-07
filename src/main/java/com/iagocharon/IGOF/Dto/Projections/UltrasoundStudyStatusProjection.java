package com.iagocharon.IGOF.Dto.Projections;

import java.util.UUID;

public interface UltrasoundStudyStatusProjection {
  public UUID getId();

  public String getName();

  public String getStatus();

  public String getReason();
}
