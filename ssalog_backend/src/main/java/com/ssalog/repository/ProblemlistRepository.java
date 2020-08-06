package com.ssalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssalog.dto.Post;
import com.ssalog.dto.problemlist;

public interface ProblemlistRepository extends MongoRepository<Post,String>{
	public Page<problemlist> findByProblemid(String problemid, Pageable pageable);
}
