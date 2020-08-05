package com.ssalog.service;

import java.util.List;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;

public interface CommentService {
	public int write_comment(String post_pk,Comment comment,String username);
	public List<Comment> read_comment(String Scoring);
	public void delete_comment(String id);
	public void update_comment(Comment comment,String id);
	public void write_subcomment(String id, Comment comment);
	public void update_subcomment(String id, Comment comment);
}
