package net.lucianolattes.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProfile {
  @XmlElement
  private String bio;

  public UserProfile() {
  }
  
  public UserProfile(String bio) {
    this.bio = bio;
  }
  
  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }  
}
