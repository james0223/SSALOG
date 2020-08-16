package com.ssalog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Problem;


public interface ProblemService {

	public Page<Problem> select_by_problemid(String problemid, PageRequest pageable);
	public Page<Problem> select_by_problemname(String problemname, PageRequest pageable);
	//public Page<Problem> findkey(List<String> keyword, PageRequest pageable);
	public Page<Problem> findkey(String[] keyword , PageRequest pageable);
	public Problem find_problemid(String problemid);

}
