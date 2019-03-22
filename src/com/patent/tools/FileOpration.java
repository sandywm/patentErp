package com.patent.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作类
 * 文件的复制，删除，文件夹的删除
 * @author wm
 *
 */
public class FileOpration {
	
	private static final int  BUFFER_SIZE = 2 * 1024;
	//复制文件
	public static boolean copyFile(String oldPath,String newPath){
		 try {            
			 int bytesum = 0;            
			 int byteread = 0; 
			 File file = new File(oldPath);
			 if (file.exists()) { //文件存在时                
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
		if(file.isFile() && file.exists()) {   
	        flag = file.delete();   
	    }
		if(!file.exists()){
			flag = true; 
		}
		return flag;
	}
	//删除文件夹里面的所有文件和文件夹
	public static boolean deleteAllFile(String path){
		File file = new File(path);
		if(!file.exists()){
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
				}else if(delfile.isDirectory()){
					deleteAllFile(path + "\\" + tempList[i]);  
				}
			}
			file.delete();  
		}
		return true;
	}
	
	/**
	 * 获取文件大小
	 * @description
	 * @author wm
	 * @date 2018-9-6 上午10:46:14
	 * @param filePath
	 * @return
	 */
	public static String getFileSize(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			return Convert.convertInputNumber_1((file.length() / 1024.0)) + "KB";
		}
		return "";
	}
	
	/**
	 * 压缩成ZIP
	 * @description
	 * @author Administrator
	 * @date 2019-3-21 下午04:20:07
	 * @param srcList 压缩文件夹路径列表
	 * @param outDir 压缩包输出路径
	 * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	public static void toZip(List<File> srcList, String outDir, boolean KeepDirStructure) throws Exception{
		OutputStream out = new FileOutputStream(new File(outDir));
//        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            compressFileList(srcList, zos, KeepDirStructure);
//            long end = System.currentTimeMillis();
//            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * 压缩文件-递归压缩方法
	 * @description
	 * @author Administrator
	 * @date 2019-3-21 上午11:58:21
	 * @param sourceFile 源文件
	 * @param zos 输出流
	 * @param name 压缩后的名称
	 * @param KeepDirStructure  是否保留原来的目录结构,
     *          true:保留目录结构;
     *          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	 private static void compressFile(File sourceFile, ZipOutputStream zos, String name,
         boolean KeepDirStructure) throws Exception{
         byte[] buf = new byte[BUFFER_SIZE];
         if(sourceFile.isFile()){
             // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
             zos.putNextEntry(new ZipEntry(name));
             // copy文件到zip输出流中
             int len;
             FileInputStream in = new FileInputStream(sourceFile);
             while ((len = in.read(buf)) != -1){
                 zos.write(buf, 0, len);
             }
             // Complete the entry
             zos.closeEntry();
             in.close();
         } else {
             File[] listFiles = sourceFile.listFiles();
             if(listFiles == null || listFiles.length == 0){
                 // 需要保留原来的文件结构时,需要对空文件夹进行处理
                 if(KeepDirStructure){
                     // 空文件夹的处理
                     zos.putNextEntry(new ZipEntry(name + "/"));
                     // 没有文件，不需要文件的copy
                     zos.closeEntry();
                 }
             }else {
                 for (File file : listFiles) {
                     // 判断是否需要保留原来的文件结构
                     if (KeepDirStructure) {
                         // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                         // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    	 compressFile(file, zos, name + "/" + file.getName(),KeepDirStructure);
                     } else {
                    	 compressFile(file, zos, file.getName(),KeepDirStructure);
                     }
                 }
             }
         }
     }
	 
	 /**
	  * 压缩文件列表
	  * @description
	  * @author Administrator
	  * @date 2019-3-21 上午11:58:37
	  * @param sourceFileList 压缩文件夹路径列表
	  * @param zos 输出流
	  * @param KeepDirStructure 是否保留原来的目录结构,
     *          true:保留目录结构;
     *          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	  * @throws Exception
	  */
	 private static void compressFileList(List<File> sourceFileList,ZipOutputStream zos, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        for (File sourceFile : sourceFileList) {
            String name = sourceFile.getName();
            if (sourceFile.isFile()) {
                zos.putNextEntry(new ZipEntry(name));
                int len;
                FileInputStream in = new FileInputStream(sourceFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            } else {
                File[] listFiles = sourceFile.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    if (KeepDirStructure) {
                        zos.putNextEntry(new ZipEntry(name + "/"));
                        zos.closeEntry();
                    }
                } else {
                    for (File file : listFiles) {
                        if (KeepDirStructure) {
                        	compressFile(file, zos, name + "/" + file.getName(),KeepDirStructure);
                        } else {
                        	compressFile(file, zos, file.getName(),KeepDirStructure);
                        }
                    }
                }
            }
        }
    }
	 
	 public static void main(String[] args) throws Exception{
		 List<File> fileList = new ArrayList<File>();
		 fileList.add(new File("D:\\案卷测试10_0000320191000028"));
		 fileList.add(new File("D:\\案卷测试11_0000320192000007"));
		 FileOpration.toZip(fileList, "d:/mytest02.zip",true);
	 }
}
