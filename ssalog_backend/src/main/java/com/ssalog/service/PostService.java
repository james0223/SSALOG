package com.ssalog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;

public interface PostService {
	public Post write_post(Post post, String username);
	public int write_comment(String post_pk,Comment comment, String username);
	public Post read_post(String post_pk);
	public int delete_post(String post_pk, String username);
	public int update_post(Post p, String username);
	public Page<Post> select_by_problemid(String problemid, PageRequest pageable);
	public Page<Post> select_by_problemname(String problemname, PageRequest pageable);
	public Page<Post> select_by_keyword(String keyword, PageRequest pageable);
	public boolean is_post(String Scoring);
	public List<Comment> read_comment(String Scoring);
	//public int write_subcomment(String uniqueid,Comment comment,String username);
	public List<Map<String, Object>> find_jandi(String username);
}
