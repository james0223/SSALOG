package com.ssalog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;

public interface PostService {
	public Post write_post(Post post);
	public void write_comment(String post_pk,Comment comment);
	public Post read_post(String post_pk);
	public void delete_post(String post_pk);
	public void update_post(Post p);
	public Page<Post> select_by_problemid(String problemid, PageRequest pageable);
	public Page<Post> select_by_problemname(String problemname, PageRequest pageable);
	public Page<Post> select_by_keyword(String keyword, PageRequest pageable);
}
