package net.lucianolattes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserInfo {
  private String username;
  private String password;
  private Boolean enabled;
  private String role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @JsonIgnore
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
