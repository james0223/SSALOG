package com.ssalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Account;
import com.ssalog.dto.AccountSub;
public interface AccountService {
	public Page<AccountSub> find_toNickname(String nickname, PageRequest pageable);
	public long find_alluser();
	public String find_username(String nickname);
}
