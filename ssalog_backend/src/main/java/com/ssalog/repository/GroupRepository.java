package com.ssalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.GroupDTO;

public interface GroupRepository extends JpaRepository<GroupDTO, Long>{
	
}
