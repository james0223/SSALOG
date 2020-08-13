package com.ssalog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Follow is_f = followRepository.findByFollower_usernameAndFollowing_nickname(follower, following);
		if(is_f == null) {
			Follow f = new Follow();
			Account followerac = accountRepository.findByUsername(follower);
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
		Follow is_f = followRepository.findByFollower_usernameAndFollowing_nickname(follower, following);
		if(is_f == null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	// 내가 팔로우 하는사람
	public Map<String,String> myfollow(String nickname){
		Map<String, String> m = new HashMap<>(); 
		List<Follow> myfollow = followRepository.findByFollower_nickname(nickname);
		for(int i=0; i<myfollow.size(); i++) {
			String nic = myfollow.get(i).getFollowing().getNickname();
			String img = myfollow.get(i).getFollowing().getImgpath();
			if(img == null) {
				img = "default.png";
			}
			m.put(nic, img);
		}
		return m;
	}
	// 나를 팔로우 하는사람
	@Override
	public Map<String, String> myfollowing(String nickname){
		Map<String, String> m = new HashMap<>(); 
		List<Follow> myfollow = followRepository.findByFollowing_nickname(nickname);
		List<Map<String, String>> list = new ArrayList<>();
		for(int i=0; i<myfollow.size(); i++) {
			String nic = myfollow.get(i).getFollower().getNickname();
			String img = myfollow.get(i).getFollower().getImgpath();
			if(img == null) {
				img = "default.png";
			}
			m.put(nic, img);
			list.add(m);
		}
		return m;
	}
	// 팔로우 취소하기
	@Override
	public String canclefollow(String username, String following) {
		Follow f = followRepository.findByFollower_usernameAndFollowing_nickname(username, following);
		if(f != null) {
			followRepository.delete(f);
			return "success";
		}else {
			return "fail";
		}
	}
}
