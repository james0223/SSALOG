package com.ssalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.dto.Comment;
import com.ssalog.service.GroupService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class GroupController {
	
	@Autowired
	GroupService groupService;
	

	@GetMapping("newuser/group/make")
	@ApiOperation(value = "[그룹 만들기] 새로운 그룹을 만든다.")
	public ResponseEntity<Void> make_group() {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
