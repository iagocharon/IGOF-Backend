package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends User {

  @ManyToMany(mappedBy = "doctors")
  @JsonIgnoreProperties("doctors")
  private List<Insurance> insurances;

  @OneToMany(mappedBy = "doctor")
  @JsonIgnoreProperties("doctor")
  private List<WorkSchedule> workSchedules;

  @OneToMany(mappedBy = "doctor")
  @JsonIgnoreProperties("doctor")
  private List<Appointment> appointments;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "doctor_specialties",
    joinColumns = @JoinColumn(name = "doctor_id"),
    inverseJoinColumns = @JoinColumn(name = "specialty_id")
  )
  @JsonIgnoreProperties("doctors")
  private List<Specialty> specialties = new ArrayList<>();

  int appointmentDuration;

  public Doctor() {
    insurances = new ArrayList<>();
    workSchedules = new ArrayList<>();
    appointments = new ArrayList<>();
  }

  public List<Insurance> getInsurances() {
    return insurances;
  }

  public void setInsurances(List<Insurance> insurances) {
    this.insurances = insurances;
  }

  public List<WorkSchedule> getWorkSchedules() {
    return workSchedules;
  }

  public void setWorkSchedules(List<WorkSchedule> workSchedules) {
    this.workSchedules = workSchedules;
  }

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public void addInsurance(Insurance insurance) {
    insurances.add(insurance);
  }

  public void removeInsurance(Insurance insurance) {
    insurances.remove(insurance);
  }

  public void addWorkSchedule(WorkSchedule workSchedule) {
    workSchedules.add(workSchedule);
  }

  public void removeWorkSchedule(WorkSchedule workSchedule) {
    workSchedules.remove(workSchedule);
  }

  public void addAppointment(Appointment appointment) {
    appointments.add(appointment);
  }

  public void removeAppointment(Appointment appointment) {
    appointments.remove(appointment);
  }

  public int getAppointmentDuration() {
    return this.appointmentDuration;
  }

  public void setAppointmentDuration(int appointmentDuration) {
    this.appointmentDuration = appointmentDuration;
  }

  public List<Specialty> getSpecialties() {
    return this.specialties;
  }

  public void setSpecialties(List<Specialty> specialties) {
    this.specialties = specialties;
  }

  public void addSpecialty(Specialty specialty) {
    this.specialties.add(specialty);
  }

  public void removeSpecialty(Specialty specialty) {
    this.specialties.remove(specialty);
  }

  public List<String> getSpecialtiesNames() {
    return this.specialties.stream().map(Specialty::getName).toList();
  }
}
