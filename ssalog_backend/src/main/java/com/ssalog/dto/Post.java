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
	private int len;
	private int memory;
	private String problemid;
	private String problemname;
	private int time;
	private String html;
	private List<String> keyword;
	private List<Comment> comment;
	private int like;
	private String regdate;
	private String regtime;
	private Boolean iswrite;
	
	public Post() {}

	public Post(String scoring, String username, String code, String language, int len, int memory, String problemid,
			String problemname, int time, String html, List<String> keyword, List<Comment> comment, int like,
			String regdate, String regtime, Boolean iswrite) {
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
		this.regdate = regdate;
		this.regtime = regtime;
		this.iswrite = iswrite;
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

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
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

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public Boolean getIswrite() {
		return iswrite;
	}

	public void setIswrite(Boolean iswrite) {
		this.iswrite = iswrite;
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