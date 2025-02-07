package com.iagocharon.IGOF.Dto;

public class UltrasoundAppointmentDto {

  public String start;
  public String end;
  public String status;
  public double plus;
  public String paymentMethodId;
  public String ultrasoundDoctorId;
  public String patientId;
  public String insuranceId;

  public UltrasoundAppointmentDto() {}

  public String getStart() {
    return this.start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return this.end;
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

  public String getUltrasoundDoctorId() {
    return this.ultrasoundDoctorId;
  }

  public void setUltrasoundDoctorId(String ultrasoundDoctorId) {
    this.ultrasoundDoctorId = ultrasoundDoctorId;
  }

  public String getPatientId() {
    return this.patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }

  public String getInsuranceId() {
    return this.insuranceId;
  }

  public void setInsuranceId(String insuranceId) {
    this.insuranceId = insuranceId;
  }
}
