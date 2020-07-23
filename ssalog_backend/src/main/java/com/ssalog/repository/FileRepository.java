package com.ssalog.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FileRepository {
	public Map<String, String> saveFile(MultipartFile multipartFile, String rootPath);
}
