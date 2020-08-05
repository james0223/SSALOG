package com.ssalog.dto;

public class keyword {
	private String name;
	private int cnt;
	public keyword() {}
	public keyword(String name, int cnt) {
		super();
		this.name = name;
		this.cnt = cnt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
