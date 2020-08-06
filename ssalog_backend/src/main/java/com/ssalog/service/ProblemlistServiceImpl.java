package com.ssalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssalog.dto.problemlist;
import com.ssalog.repository.ProblemlistRepository;

@Service
public class ProblemlistServiceImpl implements ProblemlistService{
	@Autowired
	ProblemlistRepository problemlistRepository;
	
	@Override
	public Page<problemlist> select_by_problemid(String problemid, PageRequest pageable) {
		return problemlistRepository.findByProblemid(problemid, pageable);
	}
}
