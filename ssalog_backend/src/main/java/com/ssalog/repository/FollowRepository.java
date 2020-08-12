package com.ssalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>{
	//public Follow findByFollower_usernameAndFollowing_username(String follower, String following);
}
