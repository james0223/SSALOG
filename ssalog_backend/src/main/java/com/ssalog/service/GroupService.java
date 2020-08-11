package com.ssalog.service;

import java.util.List;

import com.ssalog.dto.GroupDTO;
import com.ssalog.dto.GroupRegist;

public interface GroupService {

	public String makeGroup(GroupDTO g,String username);
	public String applyGroup(String groupname, String username);
	public List<GroupRegist> appliylist(String username,String groupname);
	public String applyaccept(String username,String groupname,Long regid);
	public String makeGoal(String username, String groupname, String problemid, String problemname, String limit);
}
