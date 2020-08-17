package com.ssalog.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.config.webhook;
import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.dto.Problem;
import com.ssalog.service.CommentService;
import com.ssalog.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class CommentController {
	@Autowired
	PostService postService;
	@Autowired
	CommentService commentService;
	
	@GetMapping("newuser/post/get_comment")
	@ApiOperation(value = "[댓글리스트가져오기] 풀이번호에 해당하는 게시물에 달린 댓글들을 가져온다.")
	public ResponseEntity<List<Comment>> read_comment(@RequestParam("Scoring") String Scoring) {
		return new ResponseEntity<List<Comment>>(commentService.read_comment(Scoring), HttpStatus.OK);
	}
	// --------- comment ------------------------------
	@PostMapping("user/post/write_comment")
	@ApiOperation(value = "[댓글작성] 풀이번호에 해당하는 게시물에 댓글을 작성한다, 로그인을 해야 작성이 가능함, 만약 풀이번호에 해당하는 게시물이 존재 하지 않으면 400 error 발생시킨다.")
	public ResponseEntity<String> write_comment(HttpServletResponse response,@RequestParam("Scoring") String Scoring,@RequestParam("Comment") String comment) {
		String username = response.getHeader("username");
		int flag = commentService.write_comment(Scoring, comment, username);
		if(flag == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("not exist!",HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("user/post/delete_comment")
	@ApiOperation(value = "[댓글삭제] 댓글의 id를 줘서 해당 댓글을 삭제한다.")
	public ResponseEntity<String> write_comment(@RequestParam("id") String id) {
		commentService.delete_comment(id);
		return new ResponseEntity<String>("삭제 완료!", HttpStatus.OK);
	}
	@PutMapping("user/post/put_comment")
	@ApiOperation(value = "[댓글업데이트] 댓글의 id와 수정할 내용을 줘서 해당 댓글을 수정한다.")
	public ResponseEntity<String> update_comment(HttpServletResponse response,@RequestParam("id") String id, @RequestParam("message") String message) {
		String username = response.getHeader("username");
		commentService.update_comment(message, id, username);
		return new ResponseEntity<String>("수정 완료!", HttpStatus.OK);
	}
	
//	@PostMapping("user/post/write_subcomment")
//	@ApiOperation(value = "[대댓글 작성] 상위 댓글의 id와 작성할 comment dto을 줘서 대댓글을 작성한다, 로그인을 해야 작성이 가능함")
//	public ResponseEntity<String> write_subcomment(@RequestParam("id") String id, @RequestBody Comment comment) {
//		commentService.update_comment(comment, id);
//		return new ResponseEntity<String>("수정 완료!", HttpStatus.OK);
//	}
//	@ApiOperation(value ="[test]")
//	@PostMapping("/newuser/test1")
//	public void test2(@RequestBody Comment comment, @RequestParam("id") String id){
//		commentService.update_comment(comment, id);
//	}
	@ApiOperation(value ="[test]")
	@PostMapping("/newuser/test2")
	public void test3(@RequestParam("id") String id, @RequestBody Comment comment){
		commentService.update_subcomment(id, comment);
	}
//	@PostMapping("user/post/write_subcomment")
//	@ApiOperation(value = "[대댓글작성] 대댓글을 작성한다, 로그인을 해야 작성이 가능함, 만약 해당하는 댓글이 존재 하지 않으면 400 error 발생시킨다.")
//	public ResponseEntity<String> write_subcomment(HttpServletResponse response,@RequestParam("uniqueid") String uniqueid,@RequestBody Comment comment) {
//		String username = response.getHeader("username");
//		int flag = postService.write_subcomment(uniqueid, comment, username);
//		if(flag == 1) {
//			return new ResponseEntity<String>("success", HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>("not exist!",HttpStatus.BAD_REQUEST);
//		}
//	}
	@ExceptionHandler(Exception.class)
	public void nullex(HttpServletResponse response,Exception e) {
		String username = response.getHeader("username");
		System.err.println("comment 부분에서 " + e.getClass());
		webhook w = new webhook();
		if(username != null) {
			w.send(username + "이 사고치는중!\n comment 부분에서 " + e.getClass());
		}else {
			w.send("comment 부분에서 " + e.getClass());
		}
	}
}
