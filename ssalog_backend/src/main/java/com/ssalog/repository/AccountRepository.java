package com.ssalog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssalog.dto.Account;
import com.ssalog.dto.AccountSub;

// CrudRepository는 관리되는 엔티티 클래스에 대해 정교한 CRUD 기능을 제공한다. 
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);	// user 검색
    Account findByNickname(String nickname);	// 닉네임 검색
    Long deleteByUsername(String username);		// user 삭제 (admin 사용자만)
    Page<AccountSub> findByNicknameLike(String nickname, Pageable pageable);
}


//그런데 만약 모든정보도 가져오고 원하는것만도 가져오고 싶을땐 어떻게 해야할까?
//
//아래와 같이 generic을 선언해주고 호출하는쪽에서 class를 파라미터로 넘겨주면 된다. 
//
//<T> List<T> findAllBy(Class<T> type);


