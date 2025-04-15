package com.iagocharon.IGOF.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(
  name = "insurance_parents",
  uniqueConstraints = {
    @jakarta.persistence.UniqueConstraint(columnNames = { "name" }),
  }
)
public class InsuranceParent {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private int dailyLimit;

  @OneToMany(mappedBy = "insuranceParent", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("insuranceParent")
  private List<Insurance> insurances;

  public InsuranceParent() {
    insurances = new ArrayList<>();
    dailyLimit = 0;
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

  public int getDailyLimit() {
    return this.dailyLimit;
  }

  public void setDailyLimit(int dailyLimit) {
    this.dailyLimit = dailyLimit;
  }

  public List<Insurance> getInsurances() {
    return this.insurances;
  }

  public void setInsurances(List<Insurance> insurances) {
    this.insurances = insurances;
  }

  public void addInsurance(Insurance insurance) {
    this.insurances.add(insurance);
  }

  public void removeInsurance(Insurance insurance) {
    this.insurances.remove(insurance);
  }
}
