
package com.ssalog.service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

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
				String rootPath = "/home/ubuntu/apps/upload"; //servletContext.getRealPath("/upload");
				Account ac = accountRepository.findByUsername(username);
				System.out.println("rootpath = " + rootPath);
				fileRepository.delete_file(username, rootPath, ac.getImgpath());
				Map<String, String> m = fileRepository.saveFile(multipartFile, rootPath, username);
				String imgPath = m.get("saveFileName");
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
	public boolean is_exist(String username) {
		Account acc = accountRepository.findByUsername(username);
		if(acc == null) {
			return false;
		}else {
			String rootPath = "/home/ubuntu/apps/upload";//servletContext.getRealPath("/upload");
			String filename = acc.getImgpath()==null?UUID.randomUUID().toString():acc.getImgpath();
			System.out.println("img path입니다 = " + filename);
			return fileRepository.is_exist(username, rootPath, filename);
		}
	}
	public String delete_file(String username) {
		Account acc = accountRepository.findByUsername(username);
		if(acc == null) {
			return "존재하지 않는 사용자 입니다.";
		}else {
			String filename = acc.getImgpath()==null?UUID.randomUUID().toString():acc.getImgpath();
			String rootPath = "/home/ubuntu/apps/upload";//servletContext.getRealPath("/upload");
			fileRepository.delete_file(username, rootPath, filename);
			System.out.println("img path입니다 = " + filename);
			acc.setImgpath(null);
			accountRepository.save(acc);
			return "삭제 완료";
		}
	}
	
	
}
