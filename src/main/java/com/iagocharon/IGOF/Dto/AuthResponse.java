package com.iagocharon.IGOF.Dto;

import com.iagocharon.IGOF.Entity.Role;
import java.util.UUID;

public class AuthResponse {

  String token;

  UUID id;

  Role role;

  String username;

  public AuthResponse(String token, UUID id, Role role, String username) {
    this.token = token;
    this.id = id;
    this.role = role;
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
