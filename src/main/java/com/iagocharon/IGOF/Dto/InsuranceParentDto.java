package com.iagocharon.IGOF.Dto;

public class InsuranceParentDto {

  private String name;

  private int dailyLimit;

  public InsuranceParentDto() {}

  public String getName() {
    return name;
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
}
