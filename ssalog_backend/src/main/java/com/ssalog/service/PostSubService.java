package com.ssalog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.PostSub;

public interface PostSubService {

	public Page<PostSub> select_by_problemid(String problemid, PageRequest pageable);
	public Page<PostSub> findMyPost(String username, PageRequest pageable);
	public Page<PostSub> select_by_problemname(String problemname, PageRequest pageable);
	public Page<PostSub> findkey(List<String> keyword, PageRequest pageable);
	public Page<PostSub> select_by_username(String username, PageRequest pageable);
	public Map<String, Integer> find_py_graph(String username);
}
