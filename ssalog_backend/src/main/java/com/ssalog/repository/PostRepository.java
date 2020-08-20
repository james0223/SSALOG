package com.ssalog.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssalog.dto.Post;
public interface PostRepository extends MongoRepository<Post,String>, additionalRepository{
	public Page<Post> findByProblemid(String problemid, Pageable pageable);
	public Post findByScoring(String Scoring);
	public List<Post> findByProblemidAndLanguage(String problemid, String language);
	public List<Post> findByProblemid(String problemid);
	public List<Post> findByUsernameAndProblemid(String username,String problemid);
	//@Query(value= "{'username' : ?0, 'keyword': {$ne:null}}", fields="{ '_id': false, 'keyword': 1}")
	@Query(value= "{$and: [{'username' : ?0},{'like':{$gt:0}}]}", fields="{ '_id': false, 'like': 1}")
	public List<Post> findlikesu(String username);
}
