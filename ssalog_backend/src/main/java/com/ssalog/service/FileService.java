package com.ssalog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	public void fileUpload(MultipartFile multipartFile, String username);
}
