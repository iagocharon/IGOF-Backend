package com.iagocharon.IGOF.Dto.Projections;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface UltrasoundAppointmentProjection {
  public UUID getId();

  public ZonedDateTime getStart();

  public ZonedDateTime getEnd();

  public String getAppointmentStatusString();

  public UUID getUltrasoundDoctorId();

  public PatientProjection getPatient();

  public InsuranceProjection getInsurance();

  public double getPlus();

  public String getPaymentMethodName();

  public String getPatientName();

  public String getPatientLastname();

  public String getInsuranceName();
}
