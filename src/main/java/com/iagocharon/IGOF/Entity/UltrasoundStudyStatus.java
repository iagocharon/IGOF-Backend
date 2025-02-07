package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iagocharon.IGOF.Dto.UltrasoundStudyReport;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ultrasound_study_status")
public class UltrasoundStudyStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "ultrasound_appointment_id")
  @JsonIgnoreProperties("studyStatuses")
  private UltrasoundAppointment ultrasoundAppointment;

  private String name;

  @Enumerated(EnumType.STRING)
  private StudyStatus status;

  private String reason;

  @OneToOne
  @JoinColumn(name = "ultrasound_study_report_id", referencedColumnName = "id")
  @JsonIgnoreProperties("ultrasoundStudyStatus")
  private UltrasoundStudyReport ultrasoundStudyReport;

  public UltrasoundStudyStatus() {}

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UltrasoundAppointment getUltrasoundAppointment() {
    return this.ultrasoundAppointment;
  }

  public void setUltrasoundAppointment(
    UltrasoundAppointment ultrasoundAppointment
  ) {
    this.ultrasoundAppointment = ultrasoundAppointment;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StudyStatus getStatus() {
    return this.status;
  }

  public void setStatus(StudyStatus status) {
    this.status = status;
  }

  public String getReason() {
    return this.reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public UltrasoundStudyReport getUltrasoundStudyReport() {
    return this.ultrasoundStudyReport;
  }

  public void setUltrasoundStudyReport(
    UltrasoundStudyReport ultrasoundStudyReport
  ) {
    this.ultrasoundStudyReport = ultrasoundStudyReport;
  }
}
