package com.ssalog.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.config.webhook;
import com.ssalog.dto.GroupDTO;
import com.ssalog.dto.GroupRegist;
import com.ssalog.dto.GroupRole;
import com.ssalog.dto.Groupdetail;
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
	public ResponseEntity<String> make_group(HttpServletResponse response,@RequestBody GroupDTO groupdto) {
		String username = response.getHeader("username"); 
		String result = groupService.makeGroup(groupdto, username);
		if(result.equals("is_exist")) {
			return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	}
	
	@PostMapping("user/grouping/apply_group")
	@ApiOperation(value = "[그룹 가입신청] 그룹에 가입을 신청합니다.")
	public ResponseEntity<String> apply_group(HttpServletResponse response, @RequestParam("groupname") String groupname, @RequestParam(required = false) String introduce) {
		String username = response.getHeader("username"); 
		return new ResponseEntity<String>(groupService.applyGroup(groupname, username, introduce),HttpStatus.OK);
	}
	
	@GetMapping("user/grouping/apply_list")
	@ApiOperation(value = "[그룹 가입신청 목록보기] 내 그룹에 가입신청한 목록을 봅니다.")
	public ResponseEntity<List<Map<String,String>>> apply_list(HttpServletResponse response, @RequestParam("groupname") String groupname) {
		String username = response.getHeader("username"); 
		return new ResponseEntity<List<Map<String,String>>>(groupService.appliylist(username, groupname),HttpStatus.OK);
	}
	

	@PostMapping("user/grouping/apply_accept")
	@ApiOperation(value = "[그룹 가입신청 수락] 내 그룹에 신청한 가입을 수락합니다.")
	public ResponseEntity<String> apply_accept(HttpServletResponse response,@RequestParam("groupname") String groupname, @RequestParam("regid") Long regid) {
		String username = response.getHeader("username"); 
		return new ResponseEntity<String>(groupService.applyaccept(username, groupname, regid),HttpStatus.OK);
	}
	
	@PostMapping("user/grouping/make_goal")
	@ApiOperation(value = "[그룹목표 설정] 그룹장이 목표에 해당하는 문제를 설정한다.")
	public ResponseEntity<String> make_goal(HttpServletResponse response,@RequestParam("groupname") String groupname,@RequestParam("problemid") String problemid,@RequestParam("problemname") String problemname,@RequestParam("limitday") @DateTimeFormat(iso = ISO.DATE_TIME) Date limit) {
		String username = response.getHeader("username"); 
		System.out.println("limit = " + limit);
		return new ResponseEntity<String>(groupService.makeGoal(username, groupname, problemid, problemname, limit),HttpStatus.OK);
	}
	@GetMapping("newuser/grouping/check_goal")
	@ApiOperation(value = "[그룹원들이 얼마나 풀었는지 확인합니다.] 내 그룹에 가입신청한 목록을 봅니다.")
	public ResponseEntity<Map<String, Boolean>> check_goal(@RequestParam("groupname") String groupname, @RequestParam("problemid") String problemid) {
		return new ResponseEntity<Map<String, Boolean>>(groupService.checkGoal(groupname, problemid),HttpStatus.OK);
	}
	
	@DeleteMapping("user/grouping/apply_reject")
	@ApiOperation(value = "[그룹 가입신청 거절] 내 그룹에 신청한 가입을 거절합니다.")
	public ResponseEntity<String> apply_reject(HttpServletResponse response, @RequestParam("groupname") String groupname, @RequestParam("regid") Long regid) {
		String username = response.getHeader("username"); 
		return new ResponseEntity<String>(groupService.applyreject(username,groupname, regid),HttpStatus.OK);
	}
	
	@GetMapping("user/grouping/myGrouplist")
	@ApiOperation(value = "[그룹 보기] 내가 가입한 그룹목록을 보여줍니다. 사용자 입장")
	public ResponseEntity<?> myGroup(HttpServletResponse response) {
		String username = response.getHeader("username"); 
		Map<String, GroupRole> m = groupService.myGroup(username);
		return new ResponseEntity<Map<String, GroupRole>>(m,HttpStatus.OK);
	}
	
//	@GetMapping("newuser/grouping/test")
//	@ApiOperation(value = "[그룹 보기] 내가 가입한 그룹목록을 보여줍니다. 사용자 입장")
//	public ResponseEntity<?> test(@RequestParam("limitday") @DateTimeFormat(iso = ISO.DATE_TIME) Date limit) {
//		groupService.teamstatus("d", "d", limit);
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("그룹 부분에서 " + e.getClass());
		webhook w = new webhook();
		w.send("그룹 부분에서 " + e.getClass());
	}
}
