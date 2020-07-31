package com.ssalog.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssalog.dto.Post;

public interface PostRepository extends MongoRepository<Post,String>{
	public Post findByUsername(String username);
	public Page<Post> findByProblemid(String problemid, Pageable pageable);
	public Page<Post> findByProblemnameLike(String problemname, Pageable pageable);
	public Page<Post> findByKeyword(String keyword, Pageable pageable);
	public Page<Post> findByKeyword(List<String> keyword, Pageable pageable);
}
