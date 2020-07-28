package com.ssalog.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	PostRepository postRepository;
	
	@Override
	public Post write_post(Post post) {
		return postRepository.save(post);
	}
	
	@Override
	public Post read_post(String post_pk) {
		return postRepository.findById(post_pk).get();
	}
	
	@Override
	@Transactional
	public void write_comment(String post_pk,Comment comment) {
		Post p = postRepository.findById(post_pk).get();
		List<Comment> clist = p.getComment();
		clist.add(comment);
		p.setComment(clist);
		postRepository.save(p);
	}
	
	@Override
	public void delete_post(String post_pk) {
		Post p = postRepository.findById(post_pk).get();
		postRepository.delete(p);
	}
	
	@Override
	public void update_post(Post p) {
		boolean flag = postRepository.existsById(p.getScoring());
		if(flag) {
			postRepository.save(p);
		}
	}
	
	@Override
	public Page<Post> select_by_problemid(String problemid, PageRequest pageable) {
		return postRepository.findByProblemid(problemid, pageable);
	}
	
	@Override
	public Page<Post> select_by_problemname(String problemname, PageRequest pageable){
		return postRepository.findByProblemnameLike(problemname, pageable);
	}
	
	@Override
	public Page<Post> select_by_keyword(String keyword, PageRequest pageable){
		return postRepository.findByKeyword(keyword, pageable);
	}
}
