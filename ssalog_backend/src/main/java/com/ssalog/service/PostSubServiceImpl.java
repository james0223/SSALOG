package com.ssalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Post;
import com.ssalog.dto.PostSub;
import com.ssalog.repository.PostSubRepository;

@Service
public class PostSubServiceImpl implements PostSubService{
	@Autowired
	PostSubRepository postSubRepository;
	
	@Override
	public Page<PostSub> select_by_problemid(String problemid, PageRequest pageable) {
		return postSubRepository.findByProblemid(problemid, pageable);
	}
	
	@Override
	public Page<PostSub> findMyPost(String username, PageRequest pageable){
		return postSubRepository.findByUsername(username, pageable);
	}
	
	@Override
	public Page<PostSub> select_by_problemname(String problemname, PageRequest pageable){
		return postSubRepository.findByProblemnameLike(problemname, pageable);
	}

	public Page<PostSub> findkey(List<String> keyword, PageRequest pageable){
		return postSubRepository.findAnyOfTheseValues(keyword, pageable);
	}
	
	@Override
	public Page<PostSub> select_by_username(String username, PageRequest pageable){
		return postSubRepository.findByUsername(username, pageable);
	}
	
}
