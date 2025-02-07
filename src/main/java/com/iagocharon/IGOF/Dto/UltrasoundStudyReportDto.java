package com.iagocharon.IGOF.Dto;

public class UltrasoundStudyReportDto {

  private String medicalRecordId;
  private String description;
  private String title;
  private String appointmentId;

  public UltrasoundStudyReportDto() {}

  public String getMedicalRecordId() {
    return this.medicalRecordId;
  }

  public void setMedicalRecordId(String medicalRecordId) {
    this.medicalRecordId = medicalRecordId;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAppointmentId() {
    return this.appointmentId;
  }

  public void setAppointmentId(String appointmentId) {
    this.appointmentId = appointmentId;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
