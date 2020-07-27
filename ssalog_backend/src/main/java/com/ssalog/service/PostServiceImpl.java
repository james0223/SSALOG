package com.ssalog.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	PostRepository postRepository;
	
	public Post write_post(Post post) {
		return postRepository.save(post);
	}
	
	public Post read_post(String post_pk) {
		return postRepository.findById(post_pk).get();
	}
	
	@Transactional
	public void write_comment(String post_pk,Comment comment) {
		System.out.println("post_pk = " + post_pk);
		Post p = postRepository.findByUsername("jso");
		List<Comment> clist = p.getComment();
		clist.add(comment);
		p.setComment(clist);
		postRepository.save(p);
	}
}
