package com.ssalog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.FileRepositoryImpl;
import com.ssalog.service.FileService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class MypageController {
	private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	
	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
	private FileService fileService;
	
	
	
	@ApiOperation(value = "[이미지 업로드]")
	@PostMapping(path="/newuser/upload_img")
	public void upload_img(@RequestParam("filename") MultipartFile multipartFile, @RequestParam("username") String username) {
		fileService.fileUpload(multipartFile, username);
		
	}
}
