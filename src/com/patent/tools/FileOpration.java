package com.patent.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;

/**
 * 文件操作类
 * 文件的复制，删除，文件夹的删除
 * @author wm
 *
 */
public class FileOpration {
	
	/**
	 * 判断文件是否存在（解决exists遇到空格找不到的异常）
	 * @description
	 * @author wm
	 * @date 2018-9-6 上午09:55:39
	 * @param filePath
	 * @return
	 */
	public static boolean checkExistFile(String filePath){
		try{
			new FileReader(new File(filePath));
			return true;
		}catch (FileNotFoundException e) {
			return false;
		}
	}
	
	//复制文件
	public static boolean copyFile(String oldPath,String newPath){
		 try {            
			 int bytesum = 0;            
			 int byteread = 0;                 
			 if (FileOpration.checkExistFile(oldPath)) { //文件存在时                
				 InputStream inStream = new FileInputStream(oldPath); //读入原文件              
				 FileOutputStream fs = new FileOutputStream(newPath);                
				 byte[] buffer = new byte[1444];                          
				 while ( (byteread = inStream.read(buffer)) != -1) {
					 bytesum += byteread; //字节数 文件大小                                  
					 fs.write(buffer, 0, byteread);               
				 }
				 inStream.close();  
				 fs.flush();
				 fs.close();
				 return true;
			 }
		 }catch (Exception e) {            
			 System.out.println("复制单个文件操作出错");            
			 e.printStackTrace();       
		 }
		return false; 
	}
	//删除文件
	public static boolean deleteFile(String filePath){
		boolean flag = false;
		File file = new File(filePath);
		if(file.isFile() && FileOpration.checkExistFile(filePath)) {   
	        flag = file.delete();   
	    }
		if(!FileOpration.checkExistFile(filePath)){
			flag = true; 
		}
		return flag;
	}
	//删除文件夹里面的所有文件和文件夹
	public static boolean deleteAllFile(String path){
		File file = new File(path);
		if(!FileOpration.checkExistFile(path)){
			return false;
		}
		if(!file.isDirectory()){
			file.delete();
		}else if(file.isDirectory()){
			String[] tempList = file.list();
			for (int i = 0; i < tempList.length; i++) {
				File delfile = new File(path + "\\" + tempList[i]); 
				if(!delfile.isDirectory()){
					delfile.delete();  
					System.out.println(delfile.getAbsolutePath() + "删除文件成功");  
				}else if(delfile.isDirectory()){
					deleteAllFile(path + "\\" + tempList[i]);  
				}
			}
			System.out.println(file.getAbsolutePath()+"删除成功");  
			file.delete();  
		}
		return true;
	}
}
