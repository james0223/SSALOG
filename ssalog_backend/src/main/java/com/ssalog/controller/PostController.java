package com.ssalog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;
import com.ssalog.config.webhook;
import com.ssalog.dto.Comment;
import com.ssalog.dto.PageRequest;
import com.ssalog.dto.Post;
import com.ssalog.service.CommentService;
import com.ssalog.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class PostController {

	@Autowired
	PostService postService;
	@Autowired
	CommentService commentService;
	
	@PostMapping("newuser/post/write")
	@ApiOperation(value = "[posting작성] extension에서 백준에서 데이터를 가져올때 사용하는 api")
	public Post write(@RequestBody Post post) {
		return postService.write_post(post);
	}
	@GetMapping("user/post/my_write_list")
	@ApiOperation(value = "[post list] 내가 작성한 post의 목록을 가져온다.")
	public Page<Post> findmypost(HttpServletResponse response, PageRequest pageable) {
		String username = response.getHeader("username"); 
		return postService.findMyPost(username,pageable.of());
	}
	@GetMapping("newuser/post/get_detail")
	@ApiOperation(value = "[posting detail] 풀이번호에 해당하는 게시물의 dto를 얻어올 수 있다. 만약 풀이번호에 해당하는 게시물이 존재 하지 않으면 400 error를 발생시킨다. ")
	public ResponseEntity<?> get_post(@RequestParam("Scoring") String Scoring){
		Post p = postService.read_post(Scoring);
		if( p != null) {
			return new ResponseEntity<Post>(p,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("not exist!",HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("user/post/delete_post")
	@ApiOperation(value = "[posting delete] 채점번호에 해당하는 게시글을 삭제함, 자기가 작성한 글만 가능, 본인 글이 아닌데 삭제하려한다? 바로 400 발생, 게시글이 존재하지 않으면 400발생")
	public ResponseEntity<String> delete_post(HttpServletResponse response,@RequestParam("Scoring") String Scoring){
		String username = response.getHeader("username");
		int flag = postService.delete_post(Scoring, username);
		if(flag == 1) {
			return new ResponseEntity<String>("삭제 완료", HttpStatus.OK);
		}else if(flag == 2){
			return new ResponseEntity<String>("권한 없음-자기글아님", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<String>("not exist!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("user/post/update_post")
	@ApiOperation(value = "[posting update] 채점번호에 해당하는 게시글을 업데이트함, 자기가 작성한 글만 가능, 본인 글이 아닌데 업데이트하려한다? 바로 400발생, 게시글이 존재하지 않으면 400발생")
	public ResponseEntity<String> update_post(HttpServletResponse response,@RequestBody Post p){
		String username = response.getHeader("username");
		int flag = postService.update_post(p, username);
		if(flag == 1) {
			return new ResponseEntity<String>("수정 완료", HttpStatus.OK);
		}else if(flag == 2){
			return new ResponseEntity<String>("권한 없음-자기글아님", HttpStatus.BAD_REQUEST);
		}else if(flag == 3) {
			return new ResponseEntity<String>("작성 완료", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("not exist!", HttpStatus.BAD_REQUEST);
		}
	}	
	
}
