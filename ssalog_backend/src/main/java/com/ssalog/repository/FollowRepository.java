package com.ssalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>{
	public Follow findByFollower_usernameAndFollowing_username(String follower, String following);
	public Follow findByFollower_nicknameAndFollowing_nickname(String follower, String following);
	public Follow findByFollower_usernameAndFollowing_nickname(String follower, String following);
	public List<Follow> findByFollower_nickname(String nickname);
	public List<Follow> findByFollowing_nickname(String nickname);
	public long countByFollower_nickname(String nickname);
	public long countByFollowing_nickname(String nickname);
}
