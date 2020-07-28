
package com.ssalog.repository;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileRepositoryImpl implements FileRepository{
	public Map<String, String> saveFile(MultipartFile multipartFile, String rootPath, String username){
		Map<String, String> map = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		String saveFolder = "resources/upload";
		String totalSaveFolder = rootPath ;//+ File.separator+ saveFolder;
		
		//System.out.println("totalSaveFolder>>>" + totalSaveFolder);
		File dir = new File(totalSaveFolder);
		if(!dir.exists())
			dir.mkdirs();
		String originFileName = multipartFile.getOriginalFilename();
		String saveFileName = username +  "." + FilenameUtils.getExtension(originFileName).toString();//UUID.randomUUID().toString() + originFileName.substring(originFileName.lastIndexOf('.'));
		
		long fileSize = 0;
		try {
			File file = new File(totalSaveFolder, saveFileName);
			multipartFile.transferTo(file);
			fileSize = file.length();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("saveFolder", saveFolder);
		map.put("saveFileName", saveFileName);
		map.put("saveFileSize", fileSize + "");
		return map;
	}
	public boolean is_exist(String username, String rootPath, String filename) {
		String saveFolder = "resources/upload";
		String totalSaveFolder = rootPath;//+ File.separator+ saveFolder;
		File file = new File(totalSaveFolder, filename);
		if(file.exists()) {
			return true;
		}else {
			return false;
		}
	}
	public void delete_file(String username, String rootPath, String filename) {
		String saveFolder = "resources/upload";
		String totalSaveFolder = rootPath;//+ File.separator+ saveFolder;
		File file = new File(totalSaveFolder, filename);
		if(file.exists()) {
			if(file.delete()){ System.out.println("파일삭제 성공"); }
			else{ System.out.println("파일삭제 실패"); }
		}else {
			System.out.println("파일이 존재하지 않습니다.");
		}
	}
}
