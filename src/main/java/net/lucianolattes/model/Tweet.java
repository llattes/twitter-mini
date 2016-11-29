package net.lucianolattes.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tweet {

  @XmlElement
  private Long id;
  @XmlElement
  private String author;
  @XmlElement
  private String content;
  @XmlElement
  private Date timestamp;
  @XmlElement
  private Boolean isRetweet;
  @XmlElement
  private Long originalId;
  @XmlElement
  private String originalAuthor;

  public Tweet() {
    this.timestamp = new Date();
    this.isRetweet = false;
    this.originalId = null;
    this.originalAuthor = null;
  }

  public Tweet(Long id, String author, String content, Boolean isRetweet, Long originalId, String originalAuthor) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.timestamp = new Date();
    this.isRetweet = isRetweet;
    this.originalId = originalId;
    this.originalAuthor = originalAuthor;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Boolean getIsRetweet() {
    return isRetweet;
  }

  public void setIsRetweet(Boolean isRetweet) {
    this.isRetweet = isRetweet;
  }

  public Long getOriginalId() {
    return originalId;
  }

  public void setOriginalId(Long originalId) {
    this.originalId = originalId;
  }

  public String getOriginalAuthor() {
    return originalAuthor;
  }

  public void setOriginalAuthor(String originalAuthor) {
    this.originalAuthor = originalAuthor;
  }
}
