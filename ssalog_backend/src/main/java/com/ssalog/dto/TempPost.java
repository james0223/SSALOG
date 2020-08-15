package com.ssalog.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class TempPost {
	
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
	public TempPost() {}
	public TempPost(String scoring, String username,String code, String language, int len, int memory,
			String problemid, String problemname, int time) {
		super();
		this.username = username;
		this.scoring = scoring;
		this.code = code;
		this.language = language;
		this.len = len;
		this.memory = memory;
		this.problemid = problemid;
		this.problemname = problemname;
		this.time = time;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getScoring() {
		return scoring;
	}
	public void setScoring(String scoring) {
		this.scoring = scoring;
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
	
}
