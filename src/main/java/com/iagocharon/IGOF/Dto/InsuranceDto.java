package com.iagocharon.IGOF.Dto;

public class InsuranceDto {

  private String name;

  private String insuranceParentId;

  public InsuranceDto() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInsuranceParentId() {
    return this.insuranceParentId;
  }

  public void setInsuranceParentId(String insuranceParentId) {
    this.insuranceParentId = insuranceParentId;
  }

  @Override
  public String toString() {
    return (
      "InsuranceDto{" +
      "name='" +
      name +
      '\'' +
      ", insuranceParentId='" +
      insuranceParentId +
      '\'' +
      '}'
    );
  }
}
