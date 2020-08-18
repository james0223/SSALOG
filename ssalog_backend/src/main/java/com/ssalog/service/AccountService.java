package com.ssalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssalog.dto.Account;
import com.ssalog.dto.AccountSub;
public interface AccountService {
	public Page<AccountSub> find_toNickname(String nickname, PageRequest pageable);
	public long find_alluser();
	public String find_username(String nickname);
	public String change_nickname(String nickname, String username);
	public Boolean change_pw(String username, String password);
	public String set_intro(String username, String intro);
	public String get_intro(String username);
	public Boolean find_pw(String username);
	public String set_form(String username, String form);
	public String get_form(String username);
	public String set_language(String username, String language);
	public String get_language(String nickname);
}
