package com.booker.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequest {

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  // Constructor — used by AuthHelper directly
  public TokenRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  // Getters
  public String getUsername() { return username; }
  public String getPassword() { return password; }
}