package com.ssalog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	 @Autowired
	 FollowService followService;
	 
	 @ApiOperation(value = "[follow하기] a가 b를 팔로우를 하는 기능입니다. 성공하면 success, 실패(이미 팔로우하는 대상)하면 fail을 return합니다.")
	 @PostMapping("user/follow/do_follow")
	 public ResponseEntity<String> do_follow(HttpServletResponse response, @RequestParam("following") String following){
		String username = response.getHeader("username");
	 	return new ResponseEntity<String>(followService.do_follow(username, following), HttpStatus.OK);
	 }
	 @ApiOperation(value = "[follow check] a가 b를 팔로우를 하는지 확인하는입니다. 성공하면 true, 실패(이미 팔로우하는 대상)하면 false를 return합니다.")
	 @GetMapping("user/follow/is_follow")
	 public ResponseEntity<Boolean> is_follow(HttpServletResponse response, @RequestParam("following") String following){
		String username = response.getHeader("username");
	 	return new ResponseEntity<Boolean>(followService.is_follow(username, following), HttpStatus.OK);
	 }
	 @ApiOperation(value = "[follow list] 닉네임을 주면 해당사람이  팔로우하는 사람들의 닉네임을 보여줍니다.")
	 @GetMapping("newuser/follow/myfollow")
	 public ResponseEntity<Map<String,String>> myfollow(@RequestParam("nickname") String nickname){
	 	return new ResponseEntity<Map<String,String>>(followService.myfollow(nickname), HttpStatus.OK);
	 }
	 @ApiOperation(value = "[following list] 닉네임을 주면 해당 사람을  팔로우한 사람들의 닉네임을 보여줍니다.")
	 @GetMapping("newuser/follow/myfollowing")
	 public ResponseEntity<Map<String,String>> myfollowing(@RequestParam("nickname") String nickname){
	 	return new ResponseEntity<Map<String,String>>(followService.myfollowing(nickname), HttpStatus.OK);
	 }
	 @ApiOperation(value = "[following 그만하기] 팔로우를 취소합니다.")
	 @DeleteMapping("user/follow/cancel_follow")
	 public ResponseEntity<String> canclefollowing(HttpServletResponse response, @RequestParam("following") String following){
		String username = response.getHeader("username");
	 	return new ResponseEntity<String>(followService.canclefollow(username, following), HttpStatus.OK);
	 }
	 @ApiOperation(value = "[왼쪽 user_info 컴포넌트용] 닉네임을 주면 해당 사람의 정보(팔로우수, 팔로윙수, 스크랩 수, 등등)를 보여줍니다")
	 @GetMapping("newuser/user_info")
	 public ResponseEntity<Map<String,Object>> user_info(@RequestParam("nickname") String nickname){
	 	return new ResponseEntity<Map<String, Object>>(followService.user_info(nickname), HttpStatus.OK);
	 }
	 @ExceptionHandler(Exception.class)
		public void nullex(HttpServletResponse response,Exception e) {
			String username = response.getHeader("username");
			System.err.println("follow 부분에서 " + e.getClass());
			webhook w = new webhook();
			if(username != null) {
				w.send(username + "이 사고치는중!\n follow 부분에서 " + e.getClass());
			}else {
				w.send("follow 부분에서 " + e.getClass());
			}
		}
}
