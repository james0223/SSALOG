package com.ssalog.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssalog.dto.Post;
public interface PostRepository extends MongoRepository<Post,String>, additionalRepository{
	public Page<Post> findByProblemid(String problemid, Pageable pageable);
	public Post findByScoring(String Scoring);
	public List<Post> findByProblemidAndLanguage(String problemid, String language);
	public List<Post> findByProblemid(String problemid);
}
