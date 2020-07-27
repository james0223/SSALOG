package com.ssalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssalog.dto.Post;

public interface PostRepository extends MongoRepository<Post,String>{
	public Post findByUsername(String username);
}
