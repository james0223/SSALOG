package com.ssalog.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
@Document
public class Comment {
	@MongoId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long _id;
	private String userid;
	private String message;
	@CreationTimestamp
	private String time;
	private int like;
	
	public Comment() {}
	
	public Comment(Long _id, String userid, String message, String time, int like) {
		super();
		this._id = _id;
		this.userid = userid;
		this.message = message;
		this.time = time;
		this.like = like;
	}

	
	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
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
