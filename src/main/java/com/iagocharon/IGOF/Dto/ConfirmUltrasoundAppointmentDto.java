package com.iagocharon.IGOF.Dto;

import java.util.List;

public class ConfirmUltrasoundAppointmentDto {

  private String status;
  private double plus;
  private String paymentMethodId;
  private List<UltrasoundStudyStatusDto> ultrasoundStudyStatus;

  public ConfirmUltrasoundAppointmentDto() {}

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getPlus() {
    return this.plus;
  }

  public void setPlus(double plus) {
    this.plus = plus;
  }

  public String getPaymentMethodId() {
    return this.paymentMethodId;
  }

  public void setPaymentMethodId(String paymentMethodId) {
    this.paymentMethodId = paymentMethodId;
  }

  public List<UltrasoundStudyStatusDto> getUltrasoundStudyStatus() {
    return this.ultrasoundStudyStatus;
  }

  public void setUltrasoundStudyStatus(
    List<UltrasoundStudyStatusDto> ultrasoundStudyStatus
  ) {
    this.ultrasoundStudyStatus = ultrasoundStudyStatus;
  }
}
