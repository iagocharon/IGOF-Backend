package com.iagocharon.IGOF.Entity;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "insurance_ultrasound_doctors",
    joinColumns = @JoinColumn(name = "insurance_id"),
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

  @OneToMany(
    mappedBy = "insurance",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL
  )
  @JsonIgnoreProperties(
    {
      "paymentMethod", "surgeryPaymentMethod", "doctor", "patient", "insurance",
    }
  )
  private List<Appointment> appointments = new ArrayList<>();

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
  private List<UltrasoundAppointment> ultrasoundAppointments =
    new ArrayList<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "insurance_parent_id")
  @JsonIgnoreProperties("insurances")
  private InsuranceParent insuranceParent;

  public Insurance() {
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
    ultrasoundAppointment.setInsurance(this);
  }

  public void removeUltrasoundAppointment(
    UltrasoundAppointment ultrasoundAppointment
  ) {
    this.ultrasoundAppointments.remove(ultrasoundAppointment);
    ultrasoundAppointment.setInsurance(null);
  }

  public InsuranceParent getInsuranceParent() {
    return this.insuranceParent;
  }

  public void setInsuranceParent(InsuranceParent insuranceParent) {
    this.insuranceParent = insuranceParent;
  }

  public String getInsuranceParentName() {
    return this.insuranceParent.getName();
  }

  public UUID getInsuranceParentId() {
    return this.insuranceParent.getId();
  }
}
