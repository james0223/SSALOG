package com.ssalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.Account;

// CrudRepository는 관리되는 엔티티 클래스에 대해 정교한 CRUD 기능을 제공한다. 
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);	// user 검색
    Account findByEmail(String email);			// email 검색
    Account findBybirthday(String birthday);	// 생일 검색
    Long deleteByUsername(String username);		// user 삭제 (admin 사용자만)
    Account findByUsernameAndEmail(String username, String email); // 비밀번호 찾기
}
