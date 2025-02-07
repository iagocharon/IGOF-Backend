package com.iagocharon.IGOF.Dto;

public class UltrasoundStudyStatusDto {

  private String name;
  private String status;
  private String reason;

  public UltrasoundStudyStatusDto() {}

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReason() {
    return this.reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String toString() {
    return (
      "UltrasoundStudyStatusDto [name=" +
      name +
      ", status=" +
      status +
      ", reason=" +
      reason +
      "]"
    );
  }
}
