package com.patent.module;

/**
 * TzsApplyFileInfo entity. @author MyEclipse Persistence Tools
 */

public class TzsApplyFileInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ZlajTzsInfoTb zlajTzsInfoTb;
	private String fileName;
	private String fileType;
	private String fileSize;

	// Constructors

	/** default constructor */
	public TzsApplyFileInfo() {
	}

	/** full constructor */
	public TzsApplyFileInfo(ZlajTzsInfoTb zlajTzsInfoTb, String fileName,
			String fileType, String fileSize) {
		this.zlajTzsInfoTb = zlajTzsInfoTb;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZlajTzsInfoTb getZlajTzsInfoTb() {
		return this.zlajTzsInfoTb;
	}

	public void setZlajTzsInfoTb(ZlajTzsInfoTb zlajTzsInfoTb) {
		this.zlajTzsInfoTb = zlajTzsInfoTb;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}