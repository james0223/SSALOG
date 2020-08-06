package com.ssalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.repository.PostRepository;


@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	PostRepository postRepository;
	
	@Override
	@Transactional
	public int write_comment(String post_pk,String comment,String username) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) {
			Post p1 = p.get();
			List<Comment> clist = p1.getComment();
			if(clist == null) {
				clist = new ArrayList<>();
			}
			String uid = username+UUID.randomUUID().toString();
			Comment c = new Comment();
			c.setMessage(comment);
			c.setUniqueid(uid);
			c.setUserid(username);
			clist.add(c);
			p1.setComment(clist);
			postRepository.save(p1);
			return 1;
		}
		else {
			return 2;
		}
	}
	
	@Override
	public List<Comment> read_comment(String Scoring){
		List<Comment> list = postRepository.findByScoring(Scoring).getComment();
		if(list == null) {
			return new ArrayList<Comment>();
		}else {
			return list;
		}
	}
	public void delete_comment(String id) {
		postRepository.delete_comment(id);
	}
	public void update_comment(Comment comment,String id) {
		postRepository.update_comment(comment,id);
	}
//	@Override
//	@Transactional
//	public int write_subcomment(String uniqueid,Comment comment,String username) {
//		Comment com= commentRepository.findByUniqueid(uniqueid);
//		if(com != null) {
//			List<Comment> clist = com.getSubcomment();
//			if(clist == null) {
//				clist = new ArrayList<>();
//			}
//			String uid = username+UUID.randomUUID().toString();
//			comment.setUniqueid(uid);
//			comment.setUserid(username);
//			clist.add(comment);
//			com.setSubcomment(clist);
//			commentRepository.save(com);
//			return 1;
//		}
//		else {
//			return 2;
//		}
//	}
	public void write_subcomment(String id, Comment comment) {
		postRepository.write_subcomment(id, comment);
	}
	public void update_subcomment(String id, Comment comment) {
		postRepository.update_subcomment(id, comment);
	}
}
