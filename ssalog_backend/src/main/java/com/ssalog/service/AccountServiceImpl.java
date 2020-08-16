package com.ssalog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Account;
import com.ssalog.dto.AccountSub;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.PostRepository;
import com.ssalog.util.Mail;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
    private PasswordEncoder bcryptEncoder;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PostRepository postRepository;
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
		Account ac2 = accountRepository.findByNickname(nickname);
		if(ac2 != null) {
			return "exist";
		}else if(ac != null) {
			ac.setNickname(nickname);
			postRepository.update_nickname(nickname, username);
			accountRepository.save(ac);
			return "sucess";
		}else {
			return "fail";
		}
	}
	
	@Override
	public Boolean change_pw(String username,String password) {
		Account ac = accountRepository.findByUsername(username);
		if(ac != null) {
	    	ac.setPassword(bcryptEncoder.encode(password));
	    	System.out.println(ac.getPassword());
	    	accountRepository.save(ac);
			return true;
		}
		return false;
	}
	@Override
	public Boolean find_pw(String username) {
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
	@Override
	public String set_intro(String username, String intro) {
		Account ac = accountRepository.findByUsername(username);
		if(ac != null) {
			ac.setIntroduce(intro);
			accountRepository.save(ac);
			return "success";
		}
		return "fail";
	}
	@Override
	public String get_intro(String username) {
		Account ac = accountRepository.findByNickname(username);
		if(ac != null) {
			return ac.getIntroduce();
		}
		return "fail";
	}
	@Override
	public String set_form(String username, String form) {
		Account ac = accountRepository.findByUsername(username);
		if(ac != null) {
			ac.setForm(form);
			accountRepository.save(ac);
			return "success";
		}
		return "fail";
	}
	@Override
	public String get_form(String username) {
		Account ac = accountRepository.findByNickname(username);
		if(ac != null) {
			return ac.getForm();
		}
		return "fail";
	}
}
