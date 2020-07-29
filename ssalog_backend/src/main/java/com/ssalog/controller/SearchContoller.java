package com.ssalog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.dto.Account;
import com.ssalog.dto.PageRequest;
import com.ssalog.dto.Post;
import com.ssalog.service.AccountService;
import com.ssalog.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("newuser/search")
public class SearchContoller {
	private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PostService postService;
	
//	@ApiOperation(value = "[회원 조회] (p-022_마이페이지) 기능: page(몇 페이지), size(개수), direction(ASC:오름차순) 인자를 입력해 회원을 조회한다. 현재는 회원가입 일자를 기준으로 정렬함")
//	@GetMapping("/all_members")
//	public ResponseEntity<Page<Account>> find_All_members(PageRequest pageable){
//		return new ResponseEntity<Page<Account>>(accountService.findAll(pageable.of()), HttpStatus.OK);
//	}
	
	@ApiOperation(value = "[회원 조회] (p-022_마이페이지) 기능: page(몇 페이지), size(개수), direction(ASC:오름차순) 인자를 입력해 회원을 조회한다. 회원 nickname을 입력받으면, nickname 기준으로 검색한다., nickname값이 없으면, 모든 회원을 조회한다 ")
	@GetMapping("/to_nickname")
	public ResponseEntity<Page<Account>> findToNickname(@RequestParam(required=false) String nickname, PageRequest pageable){
		return new ResponseEntity<Page<Account>>(accountService.find_toNickname(nickname, pageable.of()), HttpStatus.OK);
	}
	
	@ApiOperation(value ="[post problemid 조회]")
	@GetMapping("/to_problemid")
	public ResponseEntity<Page<Post>> findByproblemid(@RequestParam("problemid") String problemid, PageRequest pageable){
		return new ResponseEntity<Page<Post>>(postService.select_by_problemid(problemid, pageable.of()),HttpStatus.OK);
	}
	
	@ApiOperation(value ="[post problemname 조회]")
	@GetMapping("/to_problemname")
	public ResponseEntity<Page<Post>> findByproblemname(@RequestParam("problemname") String problemname, PageRequest pageable){
		return new ResponseEntity<Page<Post>>(postService.select_by_problemname(problemname, pageable.of()),HttpStatus.OK);
	}
	@ApiOperation(value ="[post keyword 조회]")
	@GetMapping("/to_keyword")
	public ResponseEntity<Page<Post>> findBykeyword(@RequestParam("keyword") String keyword, PageRequest pageable){
		return new ResponseEntity<Page<Post>>(postService.select_by_keyword(keyword, pageable.of()),HttpStatus.OK);
	}
	
}
