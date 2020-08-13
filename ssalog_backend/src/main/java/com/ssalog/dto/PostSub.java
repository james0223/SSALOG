package com.ssalog.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class PostSub {
	@MongoId
	private String scoring;
	private String username;
	private String nickname;
	private String language;
	private int len;
	private int memory;
	private String problemid;
	private String problemname;
	private int time;
	private int like;
	private List<String> keyword;
	private String regdate;
	private String regtime;
	private String title;
	
	
	public PostSub(String scoring, String username, String nickname, String language, int len, int memory,
			String problemid, String problemname, int time, int like, List<String> keyword, String regdate,
			String regtime, String title) {
		super();
		this.scoring = scoring;
		this.username = username;
		this.nickname = nickname;
		this.language = language;
		this.len = len;
		this.memory = memory;
		this.problemid = problemid;
		this.problemname = problemname;
		this.time = time;
		this.like = like;
		this.keyword = keyword;
		this.regdate = regdate;
		this.regtime = regtime;
		this.title = title;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
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
}
