package com.ssalog.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssalog.dto.Account;
import com.ssalog.repository.AccountRepository;
import com.ssalog.service.FileService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping()
public class MypageController {
	
	private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private FileService fileService;



	@ApiOperation(value = "[이미지 업로드] (p-014_마이페이지)프로필 사진을 변경 요청 하여 파일을 업로드, 기능 : 서버 영역에 이미지 파일이 저장된다. (파일 확장자 제한: jpg, png, jpeg) ")
	@PostMapping(path="/user/upload_profile_img")
	public ResponseEntity<String> upload_img(@RequestParam("filename") MultipartFile multipartFile, HttpServletResponse response) {
		int flag =fileService.fileUpload(multipartFile, response.getHeader("username"));
		if(flag == 1) {
			return new ResponseEntity<String>("uploadSuccess", HttpStatus.OK);
		}
		else if(flag == 2) {
			return new ResponseEntity<String>("지정된 파일 형식이 아닙니다.", HttpStatus.OK);
		}
		else if(flag == 3) {
			return new ResponseEntity<String>("파일이 존재하지 않습니다.", HttpStatus.OK);
		}
		else 
			return new ResponseEntity<String>("fail",HttpStatus.OK);
	}
	@ApiOperation(value = "[이미지 경로 얻기] (p-014_마이페이지) 기능 : 프로필 사진을 얻을 수 있는 url을 준다.ex) http://localhost:8080/upload/resources/upload/{이 요청으로 얻은 값}")
	@GetMapping(path="/newuser/get_profile_img")
	public ResponseEntity<String> get_profile_img(@RequestParam("username") String username){
		Account acc = accountRepository.findByUsername(username);
		if(acc == null) {
			return new ResponseEntity<String>("존재하지 않는 사용자 입니다.", HttpStatus.OK);
		}else if(acc.getImgpath() == null || !fileService.is_exist(username)){
			return new ResponseEntity<String>("default.png",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(acc.getImgpath(), HttpStatus.OK);
		}
	}
	@ApiOperation(value = "[프로필 삭제] (p-014_마이페이지) 기능: 현재적용된 프로필 사진을 db와 서버에서 제거하고, default 사진으로 변경한다.")
	@DeleteMapping(path="/user/delete_profile_img")
	public ResponseEntity<String> delete_profile_img(HttpServletResponse response){
		String username =  response.getHeader("username");
		return new ResponseEntity<String>(fileService.delete_file(username),HttpStatus.OK);
	}

}