package com.iagocharon.IGOF.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.UltrasoundStudyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "ultrasound_study_reports")
public class UltrasoundStudyReport {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(columnDefinition = "TEXT")
  private String report;

  private UUID doctorId;

  private String doctorName;

  private ZonedDateTime date;

  @ManyToOne
  @JoinColumn(name = "ultrasound_study_id")
  @JsonIgnoreProperties({ "ultrasoundStudyReport", "ultrasoundDoctor" })
  private Patient patient;

  @OneToOne
  @JoinColumn(name = "ultrasound_study_status_id", referencedColumnName = "id")
  @JsonIgnoreProperties("ultrasoundStudyReport")
  private UltrasoundStudyStatus ultrasoundStudyStatus;

  public UltrasoundStudyReport() {
    date = ZonedDateTime.now();
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getReport() {
    return this.report;
  }

  public void setReport(String report) {
    this.report = report;
  }

  public UUID getDoctorId() {
    return this.doctorId;
  }

  public void setDoctorId(UUID doctorId) {
    this.doctorId = doctorId;
  }

  public String getDoctorName() {
    return this.doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public ZonedDateTime getDate() {
    return this.date;
  }

  public void setDate(ZonedDateTime date) {
    this.date = date;
  }

  public Patient getPatient() {
    return this.patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public UltrasoundStudyStatus getUltrasoundStudyStatus() {
    return this.ultrasoundStudyStatus;
  }

  public void setUltrasoundStudyStatus(
    UltrasoundStudyStatus ultrasoundStudyStatus
  ) {
    this.ultrasoundStudyStatus = ultrasoundStudyStatus;
  }
}
