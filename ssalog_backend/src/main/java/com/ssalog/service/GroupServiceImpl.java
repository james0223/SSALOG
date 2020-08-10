package com.ssalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssalog.dto.GroupDTO;
import com.ssalog.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	GroupRepository groupRepository;
	
	
	public void makeGroup(GroupDTO g,String username) {
		System.out.println(g.getGroupname());
		groupRepository.save(g);
	}
}
