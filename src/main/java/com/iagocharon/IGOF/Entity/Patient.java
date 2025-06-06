package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient extends User {

  private LocalDate birthdate;

  private String phone;

  private String address;

  private String city;

  private String state;

  private String country;

  private String citizenship;

  @OneToMany(mappedBy = "patient")
  @JsonIgnoreProperties("patient")
  private List<Appointment> appointments;

  @OneToMany(mappedBy = "patient")
  @JsonIgnoreProperties("patient")
  private List<UltrasoundAppointment> ultrasoundAppointments;

  @OneToOne(mappedBy = "patient")
  @JoinColumn(name = "medical_record_id")
  @JsonIgnoreProperties("patient")
  private MedicalRecord medicalRecord;

  private String insurance;
  private String insuranceNumber;

  @ManyToOne
  @JoinColumn(name = "parent_patient_id")
  @JsonIgnoreProperties(
    {
      "childrenPatients",
      "parentPatient",
      "appointments",
      "ultrasoundAppointments",
      "medicalRecord",
    }
  )
  private Patient parentPatient;

  @OneToMany(mappedBy = "parentPatient")
  @JsonIgnoreProperties(
    {
      "childrenPatients",
      "parentPatient",
      "appointments",
      "ultrasoundAppointments",
      "medicalRecord",
    }
  )
  private List<Patient> childrenPatients = new ArrayList<>();

  public Patient() {
    appointments = new ArrayList<>();
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public void addAppointment(Appointment appointment) {
    appointments.add(appointment);
  }

  public void removeAppointment(Appointment appointment) {
    appointments.remove(appointment);
  }

  public MedicalRecord getMedicalRecord() {
    return medicalRecord;
  }

  public void setMedicalRecord(MedicalRecord medicalRecord) {
    this.medicalRecord = medicalRecord;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCitizenship() {
    return citizenship;
  }

  public void setCitizenship(String citizenship) {
    this.citizenship = citizenship;
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

  public String getInsurance() {
    return this.insurance;
  }

  public void setInsurance(String insurance) {
    this.insurance = insurance;
  }

  public String getInsuranceNumber() {
    return this.insuranceNumber;
  }

  public void setInsuranceNumber(String insuranceNumber) {
    this.insuranceNumber = insuranceNumber;
  }

  public Patient getParentPatient() {
    return this.parentPatient;
  }

  public void setParentPatient(Patient parentPatient) {
    this.parentPatient = parentPatient;
  }

  public List<Patient> getChildrenPatients() {
    return this.childrenPatients;
  }

  public void setChildrenPatients(List<Patient> childrenPatients) {
    this.childrenPatients = childrenPatients;
  }

  public void addChildrenPatient(Patient childrenPatient) {
    this.childrenPatients.add(childrenPatient);
  }

  public void removeChildrenPatient(Patient childrenPatient) {
    this.childrenPatients.remove(childrenPatient);
  }
}
