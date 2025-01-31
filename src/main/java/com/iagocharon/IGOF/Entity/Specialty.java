package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "specialties")
public class Specialty {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @ManyToMany(mappedBy = "specialties")
  @JsonIgnoreProperties("specialties")
  private List<Doctor> doctors = new ArrayList<>();

  public Specialty() {
    doctors = new ArrayList<>();
  }

  public Specialty(String name) {
    this.name = name;
    doctors = new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Doctor> getDoctors() {
    return doctors;
  }

  public void setDoctors(List<Doctor> doctors) {
    this.doctors = doctors;
  }

  public void addDoctor(Doctor doctor) {
    doctors.add(doctor);
  }

  public void removeDoctor(Doctor doctor) {
    doctors.remove(doctor);
  }
}
