package com.ssalog.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.GroupDTO;

public interface GroupRepository extends JpaRepository<GroupDTO, Long>{
	public GroupDTO findByGroupname(String groupname);
	public GroupDTO findByAccount_usernameAndGroupname(String username, String groupname);
	public GroupDTO findByAccount_username(String username);
	public GroupDTO findByAccount_nicknameAndGroupname(String nickname, String groupname);
	public List<GroupDTO> findByGroupnameLike(String groupname, Pageable pageable);
}
