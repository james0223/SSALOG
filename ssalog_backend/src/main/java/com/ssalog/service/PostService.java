package com.ssalog.service;

import java.util.List;
import java.util.Map;


import com.ssalog.dto.Post;

public interface PostService {
	public Post write_post(Post post);
	public Post read_post(String post_pk);
	public int delete_post(String post_pk, String username);
	public int update_post(Post p, String username);
	
	public List<Map<String, Object>> find_jandi(String username);
	public Map<String, Object> detail_service(String problemid, String language);
	public Map<String, Integer> detail_py(String problemid);
	public String find_problemname(String problemid);
	public void set_username(String username, String scoring);
	public long get_allpost();
}
