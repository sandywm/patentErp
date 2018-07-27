package com.patent.tools;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.upload.FormFile;

public class CheckImage
{

    public CheckImage()
    {
    }

    public boolean checkImageStuffix(String suffix){
        if(suffix.equalsIgnoreCase(".jpg") || suffix.equalsIgnoreCase(".jpeg")){
            return true;
        }else if(suffix.equalsIgnoreCase(".bmp")){
        	return true;
        }else if(suffix.equalsIgnoreCase(".gif")){
        	return true;
        }else if(suffix.equalsIgnoreCase(".png")){
        	return true;
        }else{
        	return false;
        }
    }

    public boolean checkImageSize(FormFile file, int size){
        return file.getFileSize() <= size;
    }
    public boolean checkImageSize_new(File file, int size){
        return file.length() <= size;
    }
    public boolean checkFileSize(FormFile file,int size){
    	return file.getFileSize() <= size;
    }
    /**
     * 文件大小限制
     * @description
     * @author wm
     * @date 2016-8-12 上午08:40:06
     * @param fileItem
     * @param size byte
     * @return
     */
    public boolean checkItemSize(FileItem fileItem,int size){
    	return fileItem.getSize() <= size;
    }
}