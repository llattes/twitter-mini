package net.lucianolattes.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tweet {
    @XmlElement
    private Long id;
    @XmlElement
    private String author; // Reference to another object?
    @XmlElement
    private String content;
    @XmlElement
    private Date timestamp;
    
    public Tweet() {
	this.timestamp = new Date();
    }
    
    public Tweet(Long id, String author, String content) {
	this.id = id;
	this.author = author;
	this.content = content;
	this.timestamp = new Date();
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
}
