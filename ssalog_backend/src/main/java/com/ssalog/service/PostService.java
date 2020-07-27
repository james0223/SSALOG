package com.ssalog.service;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;

public interface PostService {
	public Post write_post(Post post);
	public void write_comment(String post_pk,Comment comment);
	public Post read_post(String post_pk);
}
