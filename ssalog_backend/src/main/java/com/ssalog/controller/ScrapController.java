package com.ssalog.controller;

import java.util.List;
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
import com.ssalog.dto.Scrapedpost;
import com.ssalog.service.ScrapService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping()
public class ScrapController {
	@Autowired
	ScrapService scrapService;
	
	@ApiOperation(value = "[scrap 하기] 원하는 글을 scrap하는 기능을 가지고있습니다 글의 scoring을 넘겨주면 됩니다. 성공하면 success, 실패(이미 스크랩하는 대상)하면 fail을 return합니다.")
	@PostMapping("user/scrap/do_scrap")
	public ResponseEntity<String> do_Scrap(HttpServletResponse response, @RequestParam("Scoring") String Scoring){
		String username = response.getHeader("username");
		return new ResponseEntity<String>(scrapService.make_scrap(username, Scoring), HttpStatus.OK);
	}
	
	@ApiOperation(value = "[scrap 그만하기] 원하는 글을 scrap종료하는 기능을 가지고있습니다 글의 scoring을 넘겨주면 됩니다. 성공하면 success, 실패(이미 스크랩끊 대상)하면 fail을 return합니다.")
	@DeleteMapping("user/scrap/stop_scrap")
	public ResponseEntity<String> stop_Scrap(HttpServletResponse response, @RequestParam("Scoring") String Scoring){
		String username = response.getHeader("username");
		return new ResponseEntity<String>(scrapService.stop_scrap(username, Scoring), HttpStatus.OK);
	}
	
	@ApiOperation(value = "[scrap 하기] 해당 사용자가 스크랩 하고 있는 글을 볼 수 있습니다.")
	@PostMapping("newuser/scrap/scraplist")
	public ResponseEntity<List<Scrapedpost>> Scraplist(@RequestParam("nickname") String nickname){
		return new ResponseEntity<List<Scrapedpost>>(scrapService.get_scrapList(nickname), HttpStatus.OK);
	}
	@ApiOperation(value = "[scrap 수 찾기] 닉네임을 입력하면, 해당 사용자의 스크랩 당한 총 회수를 찾을 수 있습니다.")
	@GetMapping("newuser/scrap/scraped_su")
	public ResponseEntity<Long> Scrapsu(@RequestParam("nickname") String nickname){
		return new ResponseEntity<Long>(scrapService.findScrapsu(nickname),HttpStatus.OK);
	}
	@ApiOperation(value = "[scrap인지 아닌지] 채점번호를 입력하면, 해당글을 스크랩 중인지 아닌지 알수 있습니다. 스크랩중이면 true 아니면 false 리턴")
	@GetMapping("user/scrap/is_scrap")
	public ResponseEntity<Boolean> is_Scrap(HttpServletResponse response, @RequestParam("Scoring") String Scoring){
		String username = response.getHeader("username");
		return new ResponseEntity<Boolean>(scrapService.is_scraped(username, Scoring),HttpStatus.OK);
	}
	@ExceptionHandler(Exception.class)
	public void nullex(HttpServletResponse response,Exception e) {
		String username = response.getHeader("username");
		System.err.println("scrap 부분에서 " + e.getClass());
		webhook w = new webhook();
		if(username != null) {
			w.send(username + "이 사고치는중!\n scrap 부분에서 " + e.getClass());
		}else {
			w.send("scrap 부분에서 " + e.getClass());
		}
	}
}
