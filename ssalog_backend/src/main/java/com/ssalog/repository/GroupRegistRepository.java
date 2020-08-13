package com.ssalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.GroupRegist;

public interface GroupRegistRepository extends JpaRepository<GroupRegist, Long>{
	public GroupRegist findByGroupdto_groupnameAndAccount_username(String groupname, String username);
	public List<GroupRegist> findByGroupdto_groupname(String groupname);
	public GroupRegist findByGroupdto_groupnameAndAccount_nickname(String groupname, String nickname);
}
