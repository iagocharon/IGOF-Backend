package com.iagocharon.IGOF.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ultrasound_doctors")
public class UltrasoundDoctor extends User {

  @ManyToMany(mappedBy = "ultrasoundDoctors", fetch = FetchType.EAGER)
  @JsonIgnoreProperties({ "ultrasound_doctors" })
  private List<Insurance> insurances;

  @ManyToMany(mappedBy = "ultrasoundDoctors", fetch = FetchType.EAGER)
  @JsonIgnoreProperties({ "ultrasound_doctors" })
  private List<UltrasoundStudy> ultrasoundStudies;

  @OneToMany(mappedBy = "ultrasoundDoctor", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("ultrasoundDoctor")
  private List<UltrasoundAppointment> ultrasoundAppointments;

  @OneToMany(mappedBy = "ultrasoundDoctor", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("ultrasoundDoctor")
  private List<WorkSchedule> workSchedules;

  private int appointmentDuration;

  public UltrasoundDoctor() {
    insurances = new ArrayList<>();
    ultrasoundStudies = new ArrayList<>();
    ultrasoundAppointments = new ArrayList<>();
  }

  public List<Insurance> getInsurances() {
    return this.insurances;
  }

  public void setInsurances(List<Insurance> insurances) {
    this.insurances = insurances;
  }

  public void addInsurance(Insurance insurance) {
    this.insurances.add(insurance);
  }

  public void removeInsurance(Insurance insurance) {
    this.insurances.remove(insurance);
  }

  public List<UltrasoundStudy> getUltrasoundStudies() {
    return this.ultrasoundStudies;
  }

  public void setUltrasoundStudies(List<UltrasoundStudy> ultrasoundStudies) {
    this.ultrasoundStudies = ultrasoundStudies;
  }

  public void addUltrasoundStudy(UltrasoundStudy ultrasoundStudy) {
    this.ultrasoundStudies.add(ultrasoundStudy);
  }

  public void removeUltrasoundStudy(UltrasoundStudy ultrasoundStudy) {
    this.ultrasoundStudies.remove(ultrasoundStudy);
  }

  public List<UltrasoundAppointment> getUltrasoundAppointments() {
    return this.ultrasoundAppointments;
  }

  public void setUltrasoundAppointments(
    List<UltrasoundAppointment> ultrasoundAppointments
  ) {
    this.ultrasoundAppointments = ultrasoundAppointments;
  }

  public void addUltrasoundAppointment(
    UltrasoundAppointment ultrasoundAppointment
  ) {
    this.ultrasoundAppointments.add(ultrasoundAppointment);
  }

  public void removeUltrasoundAppointment(
    UltrasoundAppointment ultrasoundAppointment
  ) {
    this.ultrasoundAppointments.remove(ultrasoundAppointment);
  }

  public List<String> getUltrasoundStudiesNames() {
    List<String> ultrasoundStudyNames = new ArrayList<>();
    for (UltrasoundStudy ultrasoundStudy : ultrasoundStudies) {
      ultrasoundStudyNames.add(ultrasoundStudy.getName());
    }
    return ultrasoundStudyNames;
  }

  public int getAppointmentDuration() {
    return this.appointmentDuration;
  }

  public void setAppointmentDuration(int appointmentDuration) {
    this.appointmentDuration = appointmentDuration;
  }

  public List<WorkSchedule> getWorkSchedules() {
    return this.workSchedules;
  }

  public void setWorkSchedules(List<WorkSchedule> workSchedules) {
    this.workSchedules = workSchedules;
  }

  public void addWorkSchedule(WorkSchedule workSchedule) {
    this.workSchedules.add(workSchedule);
  }

  public void removeWorkSchedule(WorkSchedule workSchedule) {
    this.workSchedules.remove(workSchedule);
  }
}
