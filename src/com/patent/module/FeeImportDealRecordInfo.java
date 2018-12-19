package com.patent.module;

/**
 * FeeImportDealRecordInfo entity. @author MyEclipse Persistence Tools
 */

public class FeeImportDealRecordInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private FeeImportRecordInfo feeImportRecordInfo;
	private String zlNo;
	private String feeName;
	private String dealTime;
	private String dealStatus;
	private String dealResult;

	// Constructors

	/** default constructor */
	public FeeImportDealRecordInfo() {
	}

	public FeeImportDealRecordInfo(FeeImportRecordInfo feeImportRecordInfo,
			String zlNo, String feeName, String dealTime, String dealStatus,
			String dealResult) {
		this.feeImportRecordInfo = feeImportRecordInfo;
		this.zlNo = zlNo;
		this.feeName = feeName;
		this.dealTime = dealTime;
		this.dealStatus = dealStatus;
		this.dealResult = dealResult;
	}
	
	/** full constructor */
	public FeeImportDealRecordInfo(Integer id,FeeImportRecordInfo feeImportRecordInfo,
			String zlNo, String feeName, String dealTime, String dealStatus,
			String dealResult) {
		this.id = id;
		this.feeImportRecordInfo = feeImportRecordInfo;
		this.zlNo = zlNo;
		this.feeName = feeName;
		this.dealTime = dealTime;
		this.dealStatus = dealStatus;
		this.dealResult = dealResult;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FeeImportRecordInfo getFeeImportRecordInfo() {
		return this.feeImportRecordInfo;
	}

	public void setFeeImportRecordInfo(FeeImportRecordInfo feeImportRecordInfo) {
		this.feeImportRecordInfo = feeImportRecordInfo;
	}

	public String getZlNo() {
		return this.zlNo;
	}

	public void setZlNo(String zlNo) {
		this.zlNo = zlNo;
	}

	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealStatus() {
		return this.dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getDealResult() {
		return this.dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

}