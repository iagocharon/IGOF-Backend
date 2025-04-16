package com.iagocharon.IGOF.Dto;

import java.util.List;

public class DoctorSignupRequest {

  private String username;
  private String password;
  private String email;
  private String name;
  private String lastname;
  private List<String> specialtiesIds;
  private int appointmentDuration;
  private List<String> insurancesIds;

  public DoctorSignupRequest() {}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public int getAppointmentDuration() {
    return this.appointmentDuration;
  }

  public void setAppointmentDuration(int appointmentDuration) {
    this.appointmentDuration = appointmentDuration;
  }

  public List<String> getSpecialtyIds() {
    return this.specialtiesIds;
  }

  public void setSpecialtyIds(List<String> specialtiesIds) {
    this.specialtiesIds = specialtiesIds;
  }

  public List<String> getSpecialtiesIds() {
    return this.specialtiesIds;
  }

  public void setSpecialtiesIds(List<String> specialtiesIds) {
    this.specialtiesIds = specialtiesIds;
  }

  public List<String> getInsurancesIds() {
    return this.insurancesIds;
  }

  public void setInsurancesIds(List<String> insurancesIds) {
    this.insurancesIds = insurancesIds;
  }

  @Override
  public String toString() {
    return (
      "DoctorSignupRequest{" +
      "username='" +
      username +
      '\'' +
      ", password='" +
      password +
      '\'' +
      ", email='" +
      email +
      '\'' +
      ", name='" +
      name +
      '\'' +
      ", lastname='" +
      lastname +
      '\'' +
      ", specialtiesIds=" +
      specialtiesIds +
      ", appointmentDuration=" +
      appointmentDuration +
      ", insurancesIds=" +
      insurancesIds +
      '}'
    );
  }
}
