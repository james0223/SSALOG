package com.ssalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.problemlist;

public interface ProblemlistService {

	public Page<problemlist> select_by_problemid(String problemid, PageRequest pageable);
}
