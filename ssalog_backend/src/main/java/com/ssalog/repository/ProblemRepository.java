package com.ssalog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssalog.dto.Problem;


public interface ProblemRepository extends  MongoRepository<Problem,String>{
	public Page<Problem> findByProblemid(String problemid, Pageable pageable);
	public Page<Problem> findByNameLike(String problemname, Pageable pageable);
	public Problem findByProblemid(String problemid);
	@Query(value = "{ 'key' : {$all : [?0] }}")
	public Page<Problem> findAnyOfTheseValues(List<String> keyword,Pageable pageable);
	public List<Problem> findByStarter(String username);
}
