package com.ssalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssalog.dto.Problem;


public interface ProblemRepository extends  MongoRepository<Problem,String>{
	public Problem findByProblemid(String problemid);
}
