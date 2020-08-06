package com.ssalog.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssalog.dto.Post;
import com.ssalog.dto.problemlist;

public interface PostRepository extends MongoRepository<Post,String>, jandiRepository{
	public Page<problemlist> findByProblemid(String problemid, Pageable pageable);
	public Page<Post> findByProblemnameLike(String problemname, Pageable pageable);
	public Page<Post> findByKeyword(String keyword, Pageable pageable);
	public Page<Post> findByKeyword(List<String> keyword, Pageable pageable);
	public Post findByScoring(String Scoring);
	public Page<Post> findByUsername(String username, Pageable pageable);
	public List<Post> findByProblemidAndLanguage(String problemid, String language);
	public List<Post> findByProblemid(String problemid);
	@Query(value = "{ 'keyword' : {$all : [?0] }}")
	public Page<Post> findAnyOfTheseValues(List<String> keyword,Pageable pageable);
}
