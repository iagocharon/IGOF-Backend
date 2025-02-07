package com.iagocharon.IGOF.Dto.Projections;

import java.util.List;
import java.util.UUID;

public interface UltrasoundDoctorProjection {
  public UUID getId();

  public String getUsername();

  public String getEmail();

  public String getName();

  public String getLastname();

  public List<String> getUltrasoundStudiesNames();

  public int getAppointmentDuration();
}
