package com.iagocharon.IGOF.Dto;

public class ConfirmAppointmentDto {

  private String status;
  private double plus;
  private String paymentMethodId;
  private double surgeryPlus;
  private String surgeryPaymentMethodId;

  public ConfirmAppointmentDto() {}

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

  public double getSurgeryPlus() {
    return this.surgeryPlus;
  }

  public void setSurgeryPlus(double surgeryPlus) {
    this.surgeryPlus = surgeryPlus;
  }

  public String getSurgeryPaymentMethodId() {
    return this.surgeryPaymentMethodId;
  }

  public void setSurgeryPaymentMethodId(String surgeryPaymentMethodId) {
    this.surgeryPaymentMethodId = surgeryPaymentMethodId;
  }
}
