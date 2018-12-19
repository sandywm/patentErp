package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * FeeImportRecordInfo entity. @author MyEclipse Persistence Tools
 */

public class FeeImportRecordInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyUserInfo cpyUserInfo;
	private CpyInfoTb cpyInfoTb;
	private String excelName;
	private String uploadTime;
	private String excelPath;
	private Set<FeeImportDealRecordInfo> feeImportDealRecordInfos = new HashSet<FeeImportDealRecordInfo>();

	// Constructors

	/** default constructor */
	public FeeImportRecordInfo() {
	}

	/** minimal constructor */
	public FeeImportRecordInfo(CpyUserInfo cpyUserInfo, CpyInfoTb cpyInfoTb,
			String excelName, String uploadTime, String excelPath) {
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.excelName = excelName;
		this.uploadTime = uploadTime;
		this.excelPath = excelPath;
	}

	/** full constructor */
	public FeeImportRecordInfo(Integer id,CpyUserInfo cpyUserInfo, CpyInfoTb cpyInfoTb,
			String excelName, String uploadTime, String excelPath) {
		this.id = id;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.excelName = excelName;
		this.uploadTime = uploadTime;
		this.excelPath = excelPath;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CpyUserInfo getCpyUserInfo() {
		return this.cpyUserInfo;
	}

	public void setCpyUserInfo(CpyUserInfo cpyUserInfo) {
		this.cpyUserInfo = cpyUserInfo;
	}

	public CpyInfoTb getCpyInfoTb() {
		return this.cpyInfoTb;
	}

	public void setCpyInfoTb(CpyInfoTb cpyInfoTb) {
		this.cpyInfoTb = cpyInfoTb;
	}

	public String getExcelName() {
		return this.excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getExcelPath() {
		return this.excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public Set<FeeImportDealRecordInfo> getFeeImportDealRecordInfos() {
		return this.feeImportDealRecordInfos;
	}

	public void setFeeImportDealRecordInfos(Set<FeeImportDealRecordInfo> feeImportDealRecordInfos) {
		this.feeImportDealRecordInfos = feeImportDealRecordInfos;
	}

}