package com.iagocharon.IGOF.Dto;

public class AppointmentDto {

  private String start;
  private String end;
  private String status;
  private double plus;
  private String paymentMethodId;
  private double surgeryPlus;
  private String surgeryPaymentMethodId;
  private String doctorId;
  private String patientId;
  private String insuranceId;

  public AppointmentDto() {}

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public String getPatientId() {
    return patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
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

  public String getInsuranceId() {
    return this.insuranceId;
  }

  public void setInsuranceId(String insuranceId) {
    this.insuranceId = insuranceId;
  }
}
