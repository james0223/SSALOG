package com.ssalog.service;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.ssalog.dto.Account;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.FileRepository;

public class FileServiceImpl implements FileService{
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	ServletContext servletContext;
	public void fileUpload(MultipartFile multipartFile, String username) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String rootPath = servletContext.getContextPath();
			Map<String, String> m = fileRepository.saveFile(multipartFile, rootPath);
			
			String imgPath = m.get("saveFileName");
			Account ac = accountRepository.findByUsername(username);
			ac.setImgpath(imgPath);
			accountRepository.save(ac);
		}
	}
	
	
}
