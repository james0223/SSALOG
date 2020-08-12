package com.ssalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Account;
import com.ssalog.dto.Follow;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.FollowRepository;

@Service
@Transactional
public class FollowServiceImpl implements FollowService{

	@Autowired
	FollowRepository followRepository;

	@Autowired
	AccountRepository accountRepository;
	@Override
	public String do_follow(String follower, String following) {
//		Account ac = accountRepository.findByNickname(follower);
//		Account ac2 = accountRepository.findByNickname(following);
		Follow is_f = followRepository.findByFollower_nicknameAndFollowing_nickname(follower, following);
		if(is_f == null) {
			Follow f = new Follow();
			Account followerac = accountRepository.findByNickname(follower);
			Account followingac = accountRepository.findByNickname(following);
			if(followerac !=null && followingac != null) {
				f.setFollower(followerac);
				f.setFollowing(followingac);
				followRepository.save(f);
				return "success";
			}
			return "fail";
		}else {
			return "fail";
		}
	}
	@Override
	public Boolean is_follow(String follower, String following) {
		Follow is_f = followRepository.findByFollower_nicknameAndFollowing_nickname(follower, following);
		if(is_f == null) {
			return true;
		}else {
			return false;
		}
	}
}
