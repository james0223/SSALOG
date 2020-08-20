package com.ssalog.dto;

import java.util.List;

public interface Scrapedpost {
	public String getScoring();
	public String getTitle();
	public String getNickname();
	public String getUsername();
	public String getProblemid();
	public String getProblemname();
	public List<String> getKeyword();
}
