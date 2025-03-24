package com.iagocharon.IGOF.Dto;

import java.time.LocalDate;

public class PatientDto {

  private String username;
  private String email;
  private String name;
  private String lastname;
  private LocalDate birthdate;
  private String phone;
  private String address;
  private String city;
  private String state;
  private String country;
  private String citizenship;
  private String insurance;
  private String insuranceNumber;

  public PatientDto() {}

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public LocalDate getBirthdate() {
    return this.birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCitizenship() {
    return this.citizenship;
  }

  public void setCitizenship(String citizenship) {
    this.citizenship = citizenship;
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
}
