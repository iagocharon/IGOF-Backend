package com.iagocharon.IGOF.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurances")
public class Insurance {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "insurance_doctors",
    joinColumns = @JoinColumn(name = "insurance_id"),
    inverseJoinColumns = @JoinColumn(name = "doctor_id")
  )
  @JsonIgnoreProperties(
    { "insurances", "specialties", "workSchedules", "appointments" }
  )
  private List<Doctor> doctors;

  private int dailyLimit;

  @OneToMany(
    mappedBy = "insurance",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL
  )
  @JsonIgnoreProperties(
    {
      "insurance",
      "paymentMethod",
      "surgeryPaymentMethod",
      "doctor",
      "patient",
      "insurance",
    }
  )
  private List<Appointment> appointments = new ArrayList<>();

  public Insurance() {
    doctors = new ArrayList<>();
    dailyLimit = 0;
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

  public int getDailyLimit() {
    return this.dailyLimit;
  }

  public void setDailyLimit(int dailyLimit) {
    this.dailyLimit = dailyLimit;
  }

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public void addAppointment(Appointment appointment) {
    appointments.add(appointment);
    appointment.setInsurance(this);
  }

  public void removeAppointment(Appointment appointment) {
    appointments.remove(appointment);
    appointment.setInsurance(null);
  }
}
