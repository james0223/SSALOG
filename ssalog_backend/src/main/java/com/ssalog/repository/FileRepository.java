package com.ssalog.repository;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {
	public Map<String, String> saveFile(MultipartFile multipartFile, String rootPath, String username);
	public boolean is_exist(String username, String rootPath, String filename);
	public void delete_file(String username, String rootPath, String filename);
}
