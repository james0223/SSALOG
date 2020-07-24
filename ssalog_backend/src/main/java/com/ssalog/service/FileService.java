package com.ssalog.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	public int fileUpload(MultipartFile multipartFile, String username);
}
