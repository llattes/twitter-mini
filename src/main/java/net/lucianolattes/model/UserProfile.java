package net.lucianolattes.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProfile {
  @XmlElement
  private UserInfo userInfo;
  @XmlElement
  private String displayName;
  @XmlElement
  private String bio;

  public UserProfile() {
  }

  public UserProfile(String displayName, String bio, UserInfo userInfo) {
    this.displayName = displayName;
    this.bio = bio;
    this.userInfo = userInfo;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
