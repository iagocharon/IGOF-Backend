package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ultrasound_appointments")
public class UltrasoundAppointment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private ZonedDateTime start;

  private ZonedDateTime end;

  @ManyToOne
  @JoinColumn(name = "ultrasound_doctor_id")
  @JsonIgnoreProperties(
    {
      "insurances",
      "ultrasoundStudies",
      "ultrasoundAppointments",
      "workSchedules",
    }
  )
  private UltrasoundDoctor ultrasoundDoctor;

  @ManyToOne
  @JoinColumn(name = "patient_id")
  @JsonIgnoreProperties({ "appointments", "ultrasoundApointments" })
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "insurance_id")
  @JsonIgnoreProperties("appointments")
  private Insurance insurance;

  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;

  private double plus;

  @ManyToOne
  @JoinColumn(name = "payment_method_id")
  @JsonIgnoreProperties({ "appointments", "surgeryAppointments" })
  private PaymentMethod paymentMethod;

  @OneToMany(mappedBy = "ultrasoundAppointment")
  @JsonIgnoreProperties("ultrasoundAppointment")
  List<UltrasoundStudyStatus> ultrasoundStudyStatuses;

  public UltrasoundAppointment() {
    this.status = AppointmentStatus.INCOMPLETE;
    this.ultrasoundStudyStatuses = new ArrayList<>();
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ZonedDateTime getStart() {
    return this.start;
  }

  public void setStart(ZonedDateTime start) {
    this.start = start;
  }

  public ZonedDateTime getEnd() {
    return this.end;
  }

  public void setEnd(ZonedDateTime end) {
    this.end = end;
  }

  public UltrasoundDoctor getUltrasoundDoctor() {
    return this.ultrasoundDoctor;
  }

  public void setUltrasoundDoctor(UltrasoundDoctor ultrasoundDoctor) {
    this.ultrasoundDoctor = ultrasoundDoctor;
  }

  public Patient getPatient() {
    return this.patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public Insurance getInsurance() {
    return this.insurance;
  }

  public void setInsurance(Insurance insurance) {
    this.insurance = insurance;
  }

  public AppointmentStatus getStatus() {
    return this.status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
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

  public List<UltrasoundStudyStatus> getUltrasoundStudyStatuses() {
    return this.ultrasoundStudyStatuses;
  }

  public void setUltrasoundStudyStatuses(
    List<UltrasoundStudyStatus> ultrasoundStudyStatuses
  ) {
    this.ultrasoundStudyStatuses = ultrasoundStudyStatuses;
  }

  public void addUltrasoundStudyStatus(
    UltrasoundStudyStatus ultrasoundStudyStatus
  ) {
    this.ultrasoundStudyStatuses.add(ultrasoundStudyStatus);
  }

  public void removeUltrasoundStudyStatus(
    UltrasoundStudyStatus ultrasoundStudyStatus
  ) {
    this.ultrasoundStudyStatuses.remove(ultrasoundStudyStatus);
  }

  public int getInsuranceDailyLimit() {
    return insurance.getInsuranceParent().getDailyLimit();
  }

  public String getAppointmentStatuString() {
    return status.toString();
  }

  public String getPaymentMethodName() {
    return paymentMethod != null ? paymentMethod.getName() : null;
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

  public UUID getInsuranceId() {
    return insurance.getInsuranceParent().getId();
  }

  public UUID getUltrasoundDoctorId() {
    return ultrasoundDoctor.getId();
  }

  public String getAppointmentStatusString() {
    return status.toString();
  }

  public String getInsuranceName() {
    return insurance.getName();
  }
}
