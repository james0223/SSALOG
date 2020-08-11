package com.ssalog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Account;
import com.ssalog.dto.AccountSub;
import com.ssalog.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	AccountRepository accountRepository;
	public Page<AccountSub> find_toNickname(String nickname, PageRequest pageable){
		if(nickname == null) {
			return accountRepository.findByNicknameLike("%",pageable);
		}else {
			return accountRepository.findByNicknameLike("%"+nickname+"%", pageable);
		}
	}
	
	public long find_alluser() {
		return accountRepository.count();
	}
	
	@Override
	public String find_username(String nickname) {
		Account ac = accountRepository.findByNickname(nickname);
		if(ac != null) {
			return ac.getNickname();
		}
		return "fail";
	}
}
