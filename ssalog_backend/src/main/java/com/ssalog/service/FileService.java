
package com.ssalog.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	public int fileUpload(MultipartFile multipartFile, String username);
	public boolean is_exist(String username);
	public String delete_file(String username);
}
