package com.iagocharon.IGOF.Entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private ZonedDateTime start;

  private ZonedDateTime end;

  private double plus;

  private double surgeryPlus;

  @ManyToOne
  @JoinColumn(name = "payment_method_id")
  @JsonIgnoreProperties({ "appointments", "surgeryAppointments" })
  private PaymentMethod paymentMethod;

  @ManyToOne
  @JoinColumn(name = "surgery_payment_method_id")
  @JsonIgnoreProperties({ "appointments", "surgeryAppointments" })
  private PaymentMethod surgeryPaymentMethod;

  @ManyToOne
  @JoinColumn(name = "doctor_id")
  @JsonIgnoreProperties("appointments")
  private Doctor doctor;

  @ManyToOne
  @JoinColumn(name = "patient_id")
  @JsonIgnoreProperties("appointments")
  private Patient patient;

  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;

  @ManyToOne
  @JoinColumn(name = "insurance_id")
  @JsonIgnoreProperties("appointments")
  private Insurance insurance;

  public Appointment() {
    this.status = AppointmentStatus.INCOMPLETE;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ZonedDateTime getStart() {
    return start;
  }

  public void setStart(ZonedDateTime start) {
    this.start = start;
  }

  public ZonedDateTime getEnd() {
    return end;
  }

  public void setEnd(ZonedDateTime end) {
    this.end = end;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public UUID getDoctorId() {
    return doctor.getId();
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }

  public Patient getPatient() {
    return patient;
  }

  public UUID getPatientId() {
    return patient.getId();
  }

  public String getPatientName() {
    return patient.getName();
  }

  public String getPatientLastname() {
    return patient.getLastname();
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public double getPlus() {
    return this.plus;
  }

  public void setPlus(double plus) {
    this.plus = plus;
  }

  public PaymentMethod getPaymentMethod() {
    return this.paymentMethod;
  }

  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public double getSurgeryPlus() {
    return this.surgeryPlus;
  }

  public void setSurgeryPlus(double surgeryPlus) {
    this.surgeryPlus = surgeryPlus;
  }

  public PaymentMethod getSurgeryPaymentMethod() {
    return this.surgeryPaymentMethod;
  }

  public void setSurgeryPaymentMethod(PaymentMethod surgeryPaymentMethod) {
    this.surgeryPaymentMethod = surgeryPaymentMethod;
  }

  public AppointmentStatus getStatus() {
    return this.status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public Insurance getInsurance() {
    return this.insurance;
  }

  public void setInsurance(Insurance insurance) {
    this.insurance = insurance;
  }

  public UUID getInsuranceId() {
    return insurance.getId();
  }

  public int getInsuranceDailyLimit() {
    return insurance.getDailyLimit();
  }

  public String getAppointmentStatusString() {
    return status.toString();
  }

  public String getPaymentMethodName() {
    return paymentMethod != null ? paymentMethod.getName() : null;
  }

  public String getSurgeryPaymentMethodName() {
    return surgeryPaymentMethod != null ? surgeryPaymentMethod.getName() : null;
  }

  public String getInsuranceName() {
    return insurance.getName();
  }
}
