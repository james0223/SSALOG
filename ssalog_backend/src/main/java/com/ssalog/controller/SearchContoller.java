package com.ssalog.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.dto.Account;
import com.ssalog.dto.PageRequest;
import com.ssalog.dto.Post;
import com.ssalog.dto.Problem;
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
	public ResponseEntity<Page<Post>> findBykeyword(@RequestParam("keyword") List<String> keyword, PageRequest pageable){
		return new ResponseEntity<Page<Post>>(postService.findkey(keyword, pageable.of()),HttpStatus.OK);
	}
	@ApiOperation(value ="[잔디 심을때 필요한거] 전달한 username에 해당하는 user의 date와 count 값을 가진 json이 list형태로 날아감.")
	@GetMapping("/get_jandi")
	public ResponseEntity<?> get_jandi(@RequestParam("username") String username){
		List<Map<String, Object>> result = postService.find_jandi(username);
		if(result == null) {
			return new ResponseEntity<String>("not exist", HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Map<String, Object>>>(postService.find_jandi(username),HttpStatus.OK);
		}
	}
	@ApiOperation(value ="[detail page] 문제번호와, 언어정보를 주면, 해당 문제의 평균시간, 평균메모리 구하기")
	@GetMapping("/detail_avg")
	public Map<String,Object> detail(@RequestParam("problemid") String problemid, String language){
		return postService.detail_service(problemid, language);
	}
	@ApiOperation(value ="[detail page] 문제번호와, 페이지정보를 주면, 해당 문제를 푼사람의 정보를 보여줌")
	@GetMapping("/detail_list")
	public ResponseEntity<Page<Post>> detail_page(@PathVariable("problemid") String problemid, PageRequest pageable){
		return new ResponseEntity<Page<Post>>(postService.select_by_problemid(problemid, pageable.ofs()), HttpStatus.OK);
	}

	@ApiOperation(value ="[test]")
	@PostMapping("/test")
	public void test2(@RequestBody Problem problem){
		postService.input_problem(problem);
	}
	
}
