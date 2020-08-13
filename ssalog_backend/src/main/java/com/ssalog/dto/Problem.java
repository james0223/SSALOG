package com.ssalog.dto;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Problem {
	@MongoId
	private String problemid;
	private String name;
	private Map<String, Integer> key;
	private Map<String, solvelang> language;
	private long all_cnt;
	private String starter;
	private String starter_nickname;
	private String starter_img;
	public Problem() {}
	
	public Problem(String problemid, String name, Map<String, Integer> key, Map<String, solvelang> language,
			long all_cnt, String starter, String starter_nickname, String starter_img) {
		super();
		this.problemid = problemid;
		this.name = name;
		this.key = key;
		this.language = language;
		this.all_cnt = all_cnt;
		this.starter = starter;
		this.starter_nickname = starter_nickname;
		this.starter_img = starter_img;
	}


	public String getStarter_nickname() {
		return starter_nickname;
	}

	public void setStarter_nickname(String starter_nickname) {
		this.starter_nickname = starter_nickname;
	}

	public String getStarter_img() {
		return starter_img;
	}

	public void setStarter_img(String starter_img) {
		this.starter_img = starter_img;
	}

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	public String getProblemid() {
		return problemid;
	}
	public void setProblemid(String problemid) {
		this.problemid = problemid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Integer> getKey() {
		return key;
	}
	public void setKey(Map<String, Integer> key) {
		this.key = key;
	}
	public Map<String, solvelang> getLanguage() {
		return language;
	}
	public void setLanguage(Map<String, solvelang> language) {
		this.language = language;
	}
	public long getAll_cnt() {
		return all_cnt;
	}
	public void setAll_cnt(long all_cnt) {
		this.all_cnt = all_cnt;
	}
	@Override
	public String toString() {
		return "Problem [problemid=" + problemid + ", name=" + name + ", key=" + key + ", language=" + language
				+ ", all_cnt=" + all_cnt + "]";
	}
	
}
