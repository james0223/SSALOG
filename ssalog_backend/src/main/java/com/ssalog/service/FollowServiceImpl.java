package com.ssalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Account;
import com.ssalog.dto.Follow;
import com.ssalog.repository.FollowRepository;

@Service
@Transactional
public class FollowServiceImpl implements FollowService{

	@Autowired
	FollowRepository followRepository;

//	@Override
//	public String do_follow(String follower, String following) {
//		Follow is_f = followRepository.findByFollower_usernameAndFollowing_username(follower, following);
//		if(is_f == null) {
//			Follow f = new Follow();
//			Account followerac = new Account();
//			Account followingac = new Account();
//			followerac.setUsername(follower);
//			followingac.setUsername(following);
//			f.setFollower(followerac);
//			f.setFollowing(followingac);
//			followRepository.save(f);
//			return "success";
//		}else {
//			return "fail";
//		}
//	}
//	@Override
//	public Boolean is_follow(String follower, String following) {
//		Follow is_f = followRepository.findByFollower_usernameAndFollowing_username(follower, following);
//		if(is_f == null) {
//			return true;
//		}else {
//			return false;
//		}
//	}
}
