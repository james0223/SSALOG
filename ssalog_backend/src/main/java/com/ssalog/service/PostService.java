package com.ssalog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.dto.Problem;

public interface PostService {
	public Post write_post(Post post, String username);
	public Post read_post(String post_pk);
	public int delete_post(String post_pk, String username);
	public int update_post(Post p, String username);
	public Page<Post> select_by_problemid(String problemid, PageRequest pageable);
	public Page<Post> select_by_problemname(String problemname, PageRequest pageable);
	public Page<Post> select_by_keyword(List<String> keyword, PageRequest pageable);
	public boolean is_post(String Scoring);
	public List<Map<String, Object>> find_jandi(String username);
	public Page<Post> findMyPost(String username, PageRequest pageable);
	public Page<Post> findkey(List<String> keyword, PageRequest pageable);
	public Map<String, Object> detail_service(String problemid, String language);
	public void input_problem(Problem problem);
	public Map<String, Double> detail_py(String problemid);
	public Page<Post> select_by_username(String username, PageRequest pageable);
}
