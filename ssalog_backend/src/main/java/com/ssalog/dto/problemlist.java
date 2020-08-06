package com.ssalog.dto;

public class problemlist {
	private String username;
	private int like;
	private int time;
	private int memory;
	private String language;
	
	public problemlist() {}
	public problemlist(String username, int like, int time, int memory, String language) {
		super();
		this.username = username;
		this.like = like;
		this.time = time;
		this.memory = memory;
		this.language = language;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
}
