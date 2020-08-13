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
	public Problem() {}
	public Problem(String problemid, String name, Map<String, Integer> key, Map<String, solvelang> language,
			long all_cnt) {
		super();
		this.problemid = problemid;
		this.name = name;
		this.key = key;
		this.language = language;
		this.all_cnt = all_cnt;
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
