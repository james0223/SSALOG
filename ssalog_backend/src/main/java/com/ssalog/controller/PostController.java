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
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.ssalog.dto.PostSub;
import com.ssalog.dto.Problem;
import com.ssalog.dto.TempPost;
import com.ssalog.service.CommentService;
import com.ssalog.service.PostService;
import com.ssalog.service.PostSubService;
import com.ssalog.service.ProblemService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	PostSubService postSubService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	ProblemService problemService;
	@PostMapping("newuser/post/write")
	@ApiOperation(value = "[posting작성] extension에서 백준에서 데이터를 가져올때 사용하는 api")
	public void write(@RequestBody TempPost post) {
		postService.write_post(post);
	}
	
	@GetMapping("user/post/my_write_list")
	@ApiOperation(value = "[post list] 내가 작성한 post의 목록을 가져온다.")
	public Page<PostSub> findmypost(HttpServletResponse response, PageRequest pageable) {
		String username = response.getHeader("username"); 
		return postSubService.findMyPost(username,pageable.of());
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
	@GetMapping("newuser/post/import_view")
	@ApiOperation(value = "[posting] 백준에서 문제를 익스텐션으로 긁어왔을때, 화면에 띄우기 위한 용도, 해당하는 게시물이 존재 하지 않으면 400 error를 발생시킨다. ")
	public ResponseEntity<?> get_Temppost(@RequestParam("Scoring") String Scoring){
		TempPost p = postService.read_temppost(Scoring);
		if( p != null) {
			return new ResponseEntity<TempPost>(p,HttpStatus.OK);
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
	@GetMapping("user/post/get_username")
	@ApiOperation(value = "[username을 가져온다.] 백준에서 문제들고올때 username 삽입용입니다!, 따로 쓸 필요 없을걸요?")
	public ResponseEntity<Void> set_username(HttpServletResponse response, @RequestParam("Scoring") String scoring){
		String username = response.getHeader("username");
		postService.set_username(username, scoring);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}	
	@GetMapping("newuser/post/is_solved")
	@ApiOperation(value = "[문제 풀었는지 아닌지] 유저가 문제를 풀었는지를 리턴")
	public ResponseEntity<Boolean> is_solved(@RequestParam("username") String username, @RequestParam("problemid") String problemid){
		return new ResponseEntity<Boolean>(postService.is_solved(username, problemid),HttpStatus.OK);
		
	}	
	@GetMapping("newuser/post/is_write")
	@ApiOperation(value = "[문제로 posting을 작성했는지] ")
	public ResponseEntity<Boolean> is_write(@RequestParam("Scoring") String Scoring){
		return new ResponseEntity<Boolean>(postService.is_write(Scoring),HttpStatus.OK);
		
	}	
	
	@GetMapping("newuser/post/latestPost")
	@ApiOperation(value = "[가장 최근 post] 가장 최근에 posting된글들을 가져옵니다. count로 준 만큼 가져옴! ")
	public ResponseEntity<List<PostSub>> latestPost(@RequestParam("count") int count){
		return new ResponseEntity<List<PostSub>>(postSubService.latestPost(count),HttpStatus.OK);
		
	}		
	@GetMapping("newuser/post/starter_list")
	@ApiOperation(value = "[개척한 문제] nickname을 주면 해당 유저가 개척한 문제 목록을 보여줍니다. ")
	public ResponseEntity<List<Problem>> Starter_list(@RequestParam("nickname") String nickname){
		return new ResponseEntity<List<Problem>>(problemService.find_starter(nickname),HttpStatus.OK);
		
	}	
	@ExceptionHandler(Exception.class)
	public void nullex(HttpServletResponse response,Exception e) {
		String username = response.getHeader("username");
		System.err.println("post 부분에서 " + e.getClass());
		webhook w = new webhook();
		if(username != null) {
			w.send(username + "이 사고치는중!\n post 부분에서 " + e.getClass());
		}else {
			w.send("post 부분에서 " + e.getClass());
		}
	}
}
