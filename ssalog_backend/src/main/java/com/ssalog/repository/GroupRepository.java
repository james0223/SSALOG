package com.ssalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.Group;

public interface GroupRepository extends JpaRepository<Group, String>{
	
}
