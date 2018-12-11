package com.patent.module;

/**
 * FeeExportRecordInfo entity. @author MyEclipse Persistence Tools
 */

public class FeeExportRecordInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private CpyUserInfo user;
	private CpyInfoTb cpyInfoTb;
	private String excelName;
	private String addTime;
	private String excelPath;

	// Constructors

	/** default constructor */
	public FeeExportRecordInfo() {
	}

	/** minimal constructor */
	public FeeExportRecordInfo(CpyUserInfo user,CpyInfoTb cpyInfoTb, String excelName, String addTime,String excelPath) {
		this.user = user;
		this.cpyInfoTb = cpyInfoTb;
		this.excelName = excelName;
		this.addTime = addTime;
		this.excelPath = excelPath;
	}

	/** full constructor */
	public FeeExportRecordInfo(Integer id,CpyUserInfo user,CpyInfoTb cpyInfoTb, String excelName, String addTime,String excelPath) {
		this.id = id;
		this.user = user;
		this.cpyInfoTb = cpyInfoTb;
		this.excelName = excelName;
		this.addTime = addTime;
		this.excelPath = excelPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CpyUserInfo getUser() {
		return user;
	}

	public void setUser(CpyUserInfo user) {
		this.user = user;
	}

	public CpyInfoTb getCpyInfoTb() {
		return cpyInfoTb;
	}

	public void setCpyInfoTb(CpyInfoTb cpyInfoTb) {
		this.cpyInfoTb = cpyInfoTb;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	// Property accessors


}