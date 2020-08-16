package com.ssalog.service;

import java.util.List;
import java.util.Map;

import com.ssalog.dto.Follow;

public interface FollowService {

	public String do_follow(String follower, String following);
	public Boolean is_follow(String follower, String following);
	public Map<String,String> myfollow(String nickname);
	public Map<String, String> myfollowing(String nickname);
	public String canclefollow(String username, String following);
	public Map<String, Object> user_info(String nickname);

}
