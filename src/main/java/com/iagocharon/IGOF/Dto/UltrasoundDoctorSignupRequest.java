package com.iagocharon.IGOF.Dto;

import java.util.List;

public class UltrasoundDoctorSignupRequest {

  private String username;
  private String password;
  private String email;
  private String name;
  private String lastname;
  private List<String> ultrasoundStudiesIds;
  private int appointmentDuration;
  private List<String> insurancesIds;

  public UltrasoundDoctorSignupRequest() {}

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public List<String> getUltrasoundStudiesIds() {
    return this.ultrasoundStudiesIds;
  }

  public void setUltrasoundStudiesIds(List<String> ultrasoundStudiesIds) {
    this.ultrasoundStudiesIds = ultrasoundStudiesIds;
  }

  public int getAppointmentDuration() {
    return this.appointmentDuration;
  }

  public void setAppointmentDuration(int appointmentDuration) {
    this.appointmentDuration = appointmentDuration;
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
      "UltrasoundDoctorSignupRequest{" +
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
      ", ultrasoundStudiesIds=" +
      ultrasoundStudiesIds +
      ", appointmentDuration=" +
      appointmentDuration +
      ", insurancesIds=" +
      insurancesIds +
      '}'
    );
  }
}
