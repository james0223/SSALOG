package com.ssalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Account;

public interface AccountService {
	public Page<Account> find_toNickname(String nickname, PageRequest pageable);
	public long find_alluser();
}
