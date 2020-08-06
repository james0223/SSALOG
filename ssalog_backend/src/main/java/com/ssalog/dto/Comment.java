package com.ssalog.dto;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
@Document
public class Comment {
	@MongoId
	private String uniqueid;
	private String userid;
	private String message;
	@CreationTimestamp
	private String time;
	private int like;
	private List<Comment> subcomment;
	private String imgpath;
	public Comment() {}
	public Comment(String uniqueid, String userid, String message, String time, int like, List<Comment> subcomment,
			String imgpath) {
		super();
		this.uniqueid = uniqueid;
		this.userid = userid;
		this.message = message;
		this.time = time;
		this.like = like;
		this.subcomment = subcomment;
		this.imgpath = imgpath;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
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
	public List<Comment> getSubcomment() {
		return subcomment;
	}
	public void setSubcomment(List<Comment> subcomment) {
		this.subcomment = subcomment;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	
}
