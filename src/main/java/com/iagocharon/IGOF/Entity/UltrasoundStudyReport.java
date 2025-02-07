package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "ultrasound_study_reports")
public class UltrasoundStudyReport {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "medical_record_id")
  @JsonIgnoreProperties({ "evolutions", "patient", "ultrasoundStudyReports" })
  private MedicalRecord medicalRecord;

  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  private UUID doctorId;

  private String doctorName;

  private ZonedDateTime date;

  public UltrasoundStudyReport() {
    date = ZonedDateTime.now();
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public MedicalRecord getMedicalRecord() {
    return this.medicalRecord;
  }

  public void setMedicalRecord(MedicalRecord medicalRecord) {
    this.medicalRecord = medicalRecord;
  }

  public void addMedicalRecord(MedicalRecord medicalRecord) {
    this.medicalRecord = medicalRecord;
  }

  public void removeMedicalRecord(MedicalRecord medicalRecord) {
    this.medicalRecord = null;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
