package com.ssalog.controller;

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
import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class PostController {

	@Autowired
	PostService postService;
	
	@PostMapping("newuser/post/write")
	@ApiOperation(value = "[posting작성] 토큰에서 아이디를 찾아서 검사한다.")
	public Post write(HttpServletResponse response,@RequestBody Post post) {
		String username = response.getHeader("username"); 
		return postService.write_post(post,username);
	}
	@PostMapping("user/post/write_comment")
	@ApiOperation(value = "[댓글작성] 풀이번호에 해당하는 게시물에 댓글을 작성한다, 로그인을 해야 작성이 가능함, 만약 풀이번호에 해당하는 게시물이 존재 하지 않으면 400 error 발생시킨다.")
	public ResponseEntity<String> write_comment(HttpServletResponse response,@RequestParam("Scoring") String Scoring,@RequestBody Comment comment) {
		String username = response.getHeader("username");
		int flag = postService.write_comment(Scoring, comment, username);
		if(flag == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("not exist!",HttpStatus.BAD_REQUEST);
		}
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
	@ApiOperation(value = "[posting update] 채점번호에 해당하는 게시글을 업데이트함, 자기가 작성한 글만 가능, 본인 글이 아닌데 삭제하려한다? 바로 400발생, 게시글이 존재하지 않으면 400발생")
	public ResponseEntity<String> update_post(HttpServletResponse response,@RequestBody Post p){
		String username = response.getHeader("username");
		int flag = postService.update_post(p, username);
		if(flag == 1) {
			return new ResponseEntity<String>("수정 완료", HttpStatus.OK);
		}else if(flag == 2){
			return new ResponseEntity<String>("권한 없음-자기글아님", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<String>("not exist!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("newuser/post/is_scoring")
	@ApiOperation(value = "[is_posting] 채점번호에 해당하는 게시물이 이미 존재하는지 확인한다.")
	public ResponseEntity<Boolean> is_post(@RequestParam("Scoring") String Scoring){
		return new ResponseEntity<Boolean>(postService.is_post(Scoring), HttpStatus.OK);
	}
	
}
