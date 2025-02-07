package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ultrasound_studies")
public class UltrasoundStudy {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "ultrasound_studies_ultrasound_doctors",
    joinColumns = @JoinColumn(name = "ultrasound_studies_id"),
    inverseJoinColumns = @JoinColumn(name = "ultrasound_doctor_id")
  )
  @JsonIgnoreProperties(
    {
      "insurances",
      "ultrasoundStudies",
      "ultrasoundAppointments",
      "workSchedules",
    }
  )
  private List<UltrasoundDoctor> ultrasoundDoctors;

  private String name;

  public UltrasoundStudy() {
    this.ultrasoundDoctors = new ArrayList<>();
  }

  public UltrasoundStudy(String name) {
    this.name = name;
    this.ultrasoundDoctors = new ArrayList<>();
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public List<UltrasoundDoctor> getUltrasoundDoctors() {
    return this.ultrasoundDoctors;
  }

  public void setUltrasoundDoctors(List<UltrasoundDoctor> ultrasoundDoctors) {
    this.ultrasoundDoctors = ultrasoundDoctors;
  }

  public void addUltrasoundDoctor(UltrasoundDoctor ultrasoundDoctor) {
    this.ultrasoundDoctors.add(ultrasoundDoctor);
  }

  public void removeUltrasoundDoctor(UltrasoundDoctor ultrasoundDoctor) {
    this.ultrasoundDoctors.remove(ultrasoundDoctor);
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
