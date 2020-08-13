package com.ssalog.dto;

import java.util.List;

public class findKeyword {
	private List<String> keyword;
	
	public findKeyword() {}
	public findKeyword(List<String> keyword) {
		super();
		this.keyword = keyword;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}
	
	
}
