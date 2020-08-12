package com.ssalog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.config.webhook;
import com.ssalog.service.FollowService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class FollowController {
	 private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	 @Autowired
	 FollowService followService;
	 
//	 @ApiOperation(value = "[follow하기] a가 b를 팔로우를 하는 기능입니다. 성공하면 success, 실패(이미 팔로우하는 대상)하면 fail을 return합니다.")
//	 @PostMapping("user/follow/do_follow")
//	 public ResponseEntity<String> do_follow(@RequestParam("follower") String follower, @RequestParam("following") String following){
//	 	return new ResponseEntity<String>(followService.do_follow(follower, following), HttpStatus.OK);
//	 }
//	 @ApiOperation(value = "[follow check] a가 b를 팔로우를 하는지 확인하는입니다. 성공하면 true, 실패(이미 팔로우하는 대상)하면 false를 return합니다.")
//	 @GetMapping("user/follow/is_follow")
//	 public ResponseEntity<Boolean> is_follow(@RequestParam("follower") String follower, @RequestParam("following") String following){
//	 	return new ResponseEntity<Boolean>(followService.is_follow(follower, following), HttpStatus.OK);
//	 }
//	 
//	 @ExceptionHandler(Exception.class)
//		public void nullex(Exception e) {
//			System.err.println("follow 부분에서 " + e.getClass());
//			webhook w = new webhook();
//			w.send("follow 부분에서 " + e.getClass());
//		}
}
