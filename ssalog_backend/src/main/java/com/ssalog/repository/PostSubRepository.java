package com.ssalog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssalog.dto.Post;
import com.ssalog.dto.PostSub;

public interface PostSubRepository extends MongoRepository<Post,String>{
	public Page<PostSub> findByProblemid(String problemid, Pageable pageable);
	public Page<PostSub> findByUsername(String username, Pageable pageable);
	public Page<PostSub> findByProblemnameLike(String problemname, Pageable pageable);
	public List<PostSub> findByUsernameAndProblemid(String username, String problemid);
	@Query(value = "{ 'keyword' : {$all : [?0] }}")
	public Page<PostSub> findAnyOfTheseValues(List<String> keyword,Pageable pageable);
	
	@Query(value= "{'username' : ?0, 'keyword': {$ne:null}}", fields="{ '_id': false, 'keyword': 1}" )
	public List<PostSub> findPyGraphTouser(String username);
}
