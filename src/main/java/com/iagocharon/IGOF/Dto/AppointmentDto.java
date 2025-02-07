package com.iagocharon.IGOF.Dto;

public class AppointmentDto {

  private String start;
  private String end;
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

  public String getInsuranceId() {
    return this.insuranceId;
  }

  public void setInsuranceId(String insuranceId) {
    this.insuranceId = insuranceId;
  }
}
