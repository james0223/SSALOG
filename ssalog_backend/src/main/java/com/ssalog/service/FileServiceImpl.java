package com.ssalog.service;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssalog.dto.Account;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	ServletContext servletContext;
	public int fileUpload(MultipartFile multipartFile, String username) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String originFileName = multipartFile.getOriginalFilename();
			String type = FilenameUtils.getExtension(originFileName);
			if(type.equals("jpg") || type.equals("png") || type.equals("jpeg")) {
				String rootPath = servletContext.getRealPath("/upload");
				System.out.println("rootpath = " + rootPath);
				Map<String, String> m = fileRepository.saveFile(multipartFile, rootPath, username);

				String imgPath = m.get("saveFileName");
				Account ac = accountRepository.findByUsername(username);
				ac.setImgpath(imgPath);
				accountRepository.save(ac);
				return 1;
			}else {
				return 2;
			}
		}
		else {
			return 3;
		}
	}
	
	
}
