package com.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {
	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @return  文件名
	 */
	
	public static String upload(MultipartHttpServletRequest multiReq,String filePath) throws Exception{
	
		
		
		MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        String localFileName = StringUtils.UUID() + suffix;
        File localFile = new File(filePath + localFileName);
        if (!localFile.exists()) {
            localFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(
                    localFile);
            FileInputStream fs = (FileInputStream) multiReq.getFile("file").getInputStream();
           byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fs.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            fs.close();
            } else {
            System.out.println("文件已存在！！");
        }
        return localFileName;
      
	}
}
