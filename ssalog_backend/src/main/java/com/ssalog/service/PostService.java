package com.ssalog.service;

import java.util.List;
import java.util.Map;


import com.ssalog.dto.Post;
import com.ssalog.dto.TempPost;

public interface PostService {
	public void write_post(TempPost post);
	public Post read_post(String post_pk);
	public int delete_post(String post_pk, String username);
	public int update_post(Post p, String username);
	
	public List<Map<String, Object>> find_jandi(String username);
	public Map<String, Object> detail_service(String problemid, String language);
	public Map<String, Integer> detail_py(String problemid);
	public Map<String,String> find_problemname(String problemid);
	public void set_username(String username, String scoring);
	public long get_allpost();
	public Boolean is_solved(String username, String problemid);
	public TempPost read_temppost(String post_pk);
	public Boolean is_write(String Scoring);
}
