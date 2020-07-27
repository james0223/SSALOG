package com.ssalog.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document
public class Post {
	
	@MongoId
	private String scoring;
	
	private String username;
	private String code;
	private String language;
	private String len;
	private String memory;
	private String problemid;
	private String problemname;
	private String time;
	private String html;
	private List<String> keyword;
	private List<Comment> comment;
	private int like;
	@CreationTimestamp
	private Date regtime;
	
	public Post() {}

	public Post(String scoring, String username, String code, String language, String len, String memory,
			String problemid, String problemname, String time, String html, List<String> keyword, List<Comment> comment,
			int like, Date regtime) {
		super();
		this.scoring = scoring;
		this.username = username;
		this.code = code;
		this.language = language;
		this.len = len;
		this.memory = memory;
		this.problemid = problemid;
		this.problemname = problemname;
		this.time = time;
		this.html = html;
		this.keyword = keyword;
		this.comment = comment;
		this.like = like;
		this.regtime = regtime;
	}

	public String getScoring() {
		return scoring;
	}

	public void setScoring(String scoring) {
		this.scoring = scoring;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getProblemid() {
		return problemid;
	}

	public void setProblemid(String problemid) {
		this.problemid = problemid;
	}

	public String getProblemname() {
		return problemname;
	}

	public void setProblemname(String problemname) {
		this.problemname = problemname;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
	      if (!(o instanceof Post)) return false;
	      Post book = (Post) o;
	      return Objects.equals(getScoring(), book.getScoring());
    } 

	
}
//code: "사용자가 푼코드 "
//username: "gofeel8"
//language: "Java"
//len: "5686"
//memory: "13300"
//problem_id: "17472"
//problem_name: "다리 만들기 2"
//scoring: "21237591"
//time: "80"
//html: "  "
//keyword:[]