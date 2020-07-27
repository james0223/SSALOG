package com.ssalog.controller;

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
@RequestMapping("/newuser/post")
public class PostController {

	@Autowired
	PostService postService;
	
	@PostMapping("/write")
	@ApiOperation(value = "[posting작성] ")
	public Post write(@RequestBody Post post) {
		return postService.write_post(post);
	}
	@PostMapping("/write_comment")
	public void write_comment(@RequestParam("post_pk") String post_pk,@RequestBody Comment comment) {
		postService.write_comment(post_pk, comment);
	}
	
	@GetMapping("/get_detail")
	@ApiOperation(value = "[posting detail] ")
	public ResponseEntity<Post> get_post(@RequestParam("Scoring") String Scoring){
		return new ResponseEntity<Post>(postService.read_post(Scoring),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete_post")
	@ApiOperation(value = "[posting delete] ")
	public ResponseEntity<String> delete_post(@RequestParam("Scoring") String Scoring){
		postService.delete_post(Scoring);
		return new ResponseEntity<String>("삭제 완료", HttpStatus.OK);
	}
	
	@PutMapping("/update_post")
	@ApiOperation(value = "[posting update] ")
	public ResponseEntity<String> update_post(@RequestBody Post p){
		postService.update_post(p);
		return new ResponseEntity<String>("수정 완료", HttpStatus.OK);
	}
}
