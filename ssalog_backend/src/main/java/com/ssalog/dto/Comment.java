package com.ssalog.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Field("_id") 
	private Long post_id;
	private String userid;
	private String message;
	@CreationTimestamp
	private String time;
	private int like;
	
	public Comment() {}
	
	public Comment(Long post_id, String userid, String message, String time, int like) {
		super();
		this.post_id = post_id;
		this.userid = userid;
		this.message = message;
		this.time = time;
		this.like = like;
	}

	public Long getPost_id() {
		return post_id;
	}
	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	
}
