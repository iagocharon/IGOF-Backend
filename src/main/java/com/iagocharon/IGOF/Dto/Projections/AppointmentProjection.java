package com.iagocharon.IGOF.Dto.Projections;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface AppointmentProjection {
  public UUID getId();

  public ZonedDateTime getStart();

  public ZonedDateTime getEnd();

  public String getStatus();

  public UUID getDoctorId();

  public PatientProjection getPatient();

  public InsuranceProjection getInsurance();

  public double getPlus();

  public double getSurgeryPlus();

  public String getPaymentMethodName();

  public String getSurgeryPaymentMethodName();

  public String getInsuranceName();
}
