package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
  name = "payment_methods",
  uniqueConstraints = { @UniqueConstraint(columnNames = "name") }
)
public class PaymentMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @OneToMany(mappedBy = "paymentMethod")
  @JsonIgnoreProperties(
    { "paymentMethod", "surgeryPaymentMethod", "doctor", "patient" }
  )
  List<Appointment> appointments;

  @OneToMany(mappedBy = "paymentMethod")
  @JsonIgnoreProperties(
    { "paymentMethod", "surgeryPaymentMethod", "doctor", "patient" }
  )
  List<UltrasoundAppointment> ultrasoundAppointments;

  @OneToMany(mappedBy = "paymentMethod")
  @JsonIgnoreProperties(
    { "paymentMethod", "surgeryPaymentMethod", "doctor", "patient" }
  )
  List<Appointment> surgeryAppointments;

  public PaymentMethod() {
    this.appointments = new ArrayList<>();
    this.surgeryAppointments = new ArrayList<>();
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Appointment> getAppointments() {
    return this.appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public List<Appointment> getSurgeryAppointments() {
    return this.surgeryAppointments;
  }

  public void setSurgeryAppointments(List<Appointment> surgeryAppointments) {
    this.surgeryAppointments = surgeryAppointments;
  }

  public void addAppointment(Appointment appointment) {
    this.appointments.add(appointment);
  }

  public void addSurgeryAppointment(Appointment appointment) {
    this.surgeryAppointments.add(appointment);
  }

  public void removeAppointment(Appointment appointment) {
    this.appointments.remove(appointment);
  }

  public void removeSurgeryAppointment(Appointment appointment) {
    this.surgeryAppointments.remove(appointment);
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
}
