package com.ssalog.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.GroupDTO;
import com.ssalog.dto.GroupGoal;
import com.ssalog.dto.GroupRegist;
import com.ssalog.dto.GroupRole;
import com.ssalog.dto.Problem;

public interface GroupService {

	public String makeGroup(GroupDTO g,String username);
	public String applyGroup(String groupname, String username, String introduce);
	public List<Map<String, String>> appliylist(String username,String groupname);
	public String applyaccept(String username,String groupname,Long regid);
	public String makeGoal(String username, String groupname, String problemid, String problemname, Date limit, String mention);
	public Map<String, String> checkGoal(String groupname,String problemid);
	public String applyreject(String username,String groupname, Long regid);
	public Map<String, GroupRole> myGroup(String username);
	//public void teamstatus(String nickname, String groupname, Date limit);
	public List<Map<String,Object>> preGoal(String nickname, String groupname, PageRequest pageable);
	public List<Map<String,Object>> postGoal(String nickname, String groupname, PageRequest pageable);
	public List<Map<String, Object>> Mymember(String username, String groupname);
}
