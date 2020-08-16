package com.ssalog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Account;
import com.ssalog.dto.PostSub;
import com.ssalog.dto.Problem;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.PostRepository;
import com.ssalog.repository.ProblemRepository;

@Service
public class ProblemServiceImpl implements ProblemService{
	@Autowired
	private ProblemRepository problemRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PostRepository postRepository;
	@Override
	public Page<Problem> select_by_problemid(String problemid, PageRequest pageable) {
		return problemRepository.findByProblemid(problemid, pageable);
	}
	
	@Override
	public Page<Problem> select_by_problemname(String problemname, PageRequest pageable){
		return problemRepository.findByNameLike(problemname, pageable);
	}
	
	@Override
	public Page<Problem> findkey(String[] keyword, PageRequest pageable){
		return postRepository.keyword_search(keyword, pageable);
	}
	
	@Override
	public Problem find_problemid(String problemid) {
		return problemRepository.findByProblemid(problemid);
	}
	
	@Override
	public List<Problem> find_starter(String nickname){
		Account ac = accountRepository.findByNickname(nickname);
		List<Problem> p = new ArrayList<>();
		if(ac!= null) {
			p = problemRepository.findByStarter(ac.getUsername());
		}
		return p;
	}
}
