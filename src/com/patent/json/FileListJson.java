package com.patent.json;

/**
 * 电子回单的文件列表JSON
 * @author Administrator
 * @createDate 2018-11-4
 */
public class FileListJson {

	private String fileName;
	private String fileType;
	private String fileSize;
	
	public FileListJson(){}
	
	public FileListJson(String fileName, String fileType, String fileSize) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}
