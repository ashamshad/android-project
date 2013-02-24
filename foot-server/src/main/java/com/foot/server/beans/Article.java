package com.foot.server.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "article")
@Entity
@Table(name = "ARTICLE")
public class Article implements Serializable{
	
	@Id
	private long id;
	
	private String title;
	private String description;
	private String link;
	private long content_site_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public long getContent_site_id() {
		return content_site_id;
	}
	public void setContent_site_id(long content_site_id) {
		this.content_site_id = content_site_id;
	}
}
