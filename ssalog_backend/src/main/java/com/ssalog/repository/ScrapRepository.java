package com.ssalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long>{
	public Scrap findByScrap_usernameAndScraped(String username, String Scoring);
	public List<Scrap> findByScrap_username(String username);
	public List<Scrap> findByScrap_nickname(String nickname);
}
