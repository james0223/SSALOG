package com.ssalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssalog.dto.PostSub;
import com.ssalog.dto.Problem;
import com.ssalog.repository.ProblemRepository;

@Service
public class ProblemServiceImpl implements ProblemService{
	@Autowired
	private ProblemRepository problemRepository;
	@Override
	public Page<Problem> select_by_problemid(String problemid, PageRequest pageable) {
		return problemRepository.findByProblemid(problemid, pageable);
	}
	
	@Override
	public Page<Problem> select_by_problemname(String problemname, PageRequest pageable){
		return problemRepository.findByNameLike(problemname, pageable);
	}
	
	@Override
	public Page<Problem> findkey(List<String> keyword, PageRequest pageable){
		return problemRepository.findAnyOfTheseValues(keyword, pageable);
	}
}
