package com.ssalog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Account;
import com.ssalog.dto.AccountSub;
import com.ssalog.repository.AccountRepository;
import com.ssalog.util.Mail;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
    private PasswordEncoder bcryptEncoder;
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
			return ac.getUsername();
		}else {
			return "fail";
		}
	}
	@Override
	public String change_nickname(String nickname, String username) {
		Account ac = accountRepository.findByUsername(username);
		if(ac != null) {
			ac.setNickname(nickname);
			accountRepository.save(ac);
			return "sucess";
		}else {
			return "fail";
		}
	}
	
	@Override
	public Boolean change_pw(String username) {
		Account ac = accountRepository.findByUsername(username);
		if(ac != null) {
			Mail m = new Mail();
	    	String ran = m.sendMail(username,2);
	    	ac.setPassword(bcryptEncoder.encode(ran));
	    	accountRepository.save(ac);
			return true;
		}
		return false;
	}
}
