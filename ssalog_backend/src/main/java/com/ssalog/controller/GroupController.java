package com.ssalog.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.dto.GroupDTO;
import com.ssalog.dto.GroupRegist;
import com.ssalog.repository.GroupRegistRepository;
import com.ssalog.service.GroupService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class GroupController {
	
	@Autowired
	GroupService groupService;
	
	@PostMapping("user/grouping/make_group")
	@ApiOperation(value = "[그룹 만들기] 새로운 그룹을 만든다.")
	public ResponseEntity<Void> make_group(HttpServletResponse response,@RequestBody GroupDTO groupdto) {
		String username = response.getHeader("username"); 
		groupService.makeGroup(groupdto, username);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("user/grouping/apply_group")
	@ApiOperation(value = "[그룹 가입신청] 그룹에 가입을 신청합니다.")
	public ResponseEntity<String> apply_group(HttpServletResponse response) {
		return new ResponseEntity<String>(groupService.applyGroup("ssafy", "jso"),HttpStatus.OK);
	}
	
	@GetMapping("user/grouping/apply_list")
	@ApiOperation(value = "[그룹 가입신청 목록보기] 내 그룹에 가입신청한 목록을 봅니다.")
	public ResponseEntity<List<GroupRegist>> apply_list(HttpServletResponse response, @RequestParam("groupname") String groupname) {
		String username = response.getHeader("username"); 
		return new ResponseEntity<List<GroupRegist>>(groupService.appliylist(username, groupname),HttpStatus.OK);
	}
	
	@PostMapping("user/grouping/apply_accept")
	@ApiOperation(value = "[그룹 가입신청 수락] 내 그룹에 신청한 가입을 수락합니다.")
	public ResponseEntity<String> apply_accept(HttpServletResponse response,@RequestParam("groupname") String groupname, @RequestParam("regid") Long regid) {
		String username = response.getHeader("username"); 
		return new ResponseEntity<String>(groupService.applyaccept(username, groupname, regid),HttpStatus.OK);
	}
}
