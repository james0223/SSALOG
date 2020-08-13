package com.ssalog.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.ssalog.dto.GroupGoal;

public interface GroupGoalRepository extends JpaRepository<GroupGoal, Long>{
	public List<GroupGoal> findByDateGreaterThanAndGroupdto_groupname(@DateTimeFormat(iso = ISO.DATE_TIME) Date limit,String groupname); 
	public List<GroupGoal> findByDateLessThanAndGroupdto_groupname(@DateTimeFormat(iso = ISO.DATE_TIME) Date limit,String groupname);
	public GroupGoal findByProblemidAndGroupdto_groupname(String problemid,String groupname);
}
