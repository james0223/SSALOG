package com.ssalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
//	@Autowired
//	CommentRepository commentRepository;

	@Override
	public Post write_post(Post post,String username) {
		post.setUsername(username);
		return postRepository.save(post);
	}

	@Override
	public Post read_post(String post_pk) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) {
			return postRepository.findById(post_pk).get();
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public int write_comment(String post_pk,Comment comment,String username) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) {
			Post p1 = p.get();
			List<Comment> clist = p1.getComment();
			if(clist == null) {
				clist = new ArrayList<>();
			}
			String uid = username+UUID.randomUUID().toString();
			comment.setUniqueid(uid);
			comment.setUserid(username);
			clist.add(comment);
			p1.setComment(clist);
			postRepository.save(p1);
			return 1;
		}
		else {
			return 2;
		}
	}

	@Override
	public int delete_post(String post_pk, String username) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) { // 존재하면
			Post p1 = p.get();
			if(p1.getUsername().equals(username)) {
				postRepository.delete(p1);
				return 1;
			}else {
				return 2;
			}
		}else {
			return 3;
		}
	}

	@Override
	public int update_post(Post p, String username) {
		Optional<Post> p1 = postRepository.findById(p.getScoring());
		if(p1.isPresent()) {
			//System.out.println(username + " 사람이름? " + p.getUsername() );
			//if(p1.get().getUsername().equals(username)) {
				System.out.println("여기아냐?");
				postRepository.save(p);
				return 1;
			//}else {
			//	System.out.println("여기라고?");
			//	return 2;
			//}
		}else {
			return 3;
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

	public boolean is_post(String Scoring) {
		Optional<Post> p = postRepository.findById(Scoring);
		if(p.isPresent()) {
			return true;
		}else {
			return false;
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

}
