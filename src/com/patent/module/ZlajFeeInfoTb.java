package com.patent.module;

/**
 * ZlajFeeInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajFeeInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private FeeTypeInfoTb feeTypeInfoTb;
	private CpyUserInfo cpyUserInfo;
	private CpyInfoTb cpyInfoTb;
	private ZlajMainInfoTb zlajMainInfoTb;
	private Double feePrice;
	private Double feeRate;
	private String feeEndDateJj;
	private String feeEndDateGf;
	private String feeRemark;
	private Integer feeStatus;
	private Integer djStatus;
	private String feeJnDate;
	private String feeUpZd;
	private String tzsArea;
	private Integer yearFeeNo;
	private String feeRange;
	private Integer addStatus;

	// Constructors

	/** default constructor */
	public ZlajFeeInfoTb() {
	}

	/** minimal constructor */
	public ZlajFeeInfoTb(FeeTypeInfoTb feeTypeInfoTb, CpyUserInfo cpyUserInfo,
			CpyInfoTb cpyInfoTb, ZlajMainInfoTb zlajMainInfoTb,Double feePrice, Double feeRate,String feeEndDateJj,
			String feeEndDateGf, String feeRemark, Integer feeStatus,
			Integer djStatus, String feeJnDate, String feeUpZd,String tzsArea,Integer yearFeeNo,String feeRange,Integer addStatus) {
		this.feeTypeInfoTb = feeTypeInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.feePrice = feePrice;
		this.feeRate = feeRate;
		this.feeEndDateJj = feeEndDateJj;
		this.feeEndDateGf = feeEndDateGf;
		this.feeRemark = feeRemark;
		this.feeStatus = feeStatus;
		this.djStatus = djStatus;
		this.feeJnDate = feeJnDate;
		this.feeUpZd = feeUpZd;
		this.tzsArea = tzsArea;
		this.yearFeeNo = yearFeeNo;
		this.feeRange = feeRange;
		this.addStatus = addStatus;
	}

	/** full constructor */
	public ZlajFeeInfoTb(Integer id,FeeTypeInfoTb feeTypeInfoTb, CpyUserInfo cpyUserInfo,
			CpyInfoTb cpyInfoTb, ZlajMainInfoTb zlajMainInfoTb,Double feePrice, Double feeRate,String feeEndDateJj,
			String feeEndDateGf, String feeRemark, Integer feeStatus,
			Integer djStatus, String feeJnDate, String feeUpZd,String tzsArea,Integer yearFeeNo,String feeRange,Integer addStatus) {
		this.feeTypeInfoTb = feeTypeInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.feePrice = feePrice;
		this.feeRate = feeRate;
		this.feeEndDateJj = feeEndDateJj;
		this.feeEndDateGf = feeEndDateGf;
		this.feeRemark = feeRemark;
		this.feeStatus = feeStatus;
		this.djStatus = djStatus;
		this.feeJnDate = feeJnDate;
		this.feeUpZd = feeUpZd;
		this.tzsArea = tzsArea;
		this.yearFeeNo = yearFeeNo;
		this.feeRange = feeRange;
		this.addStatus = addStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FeeTypeInfoTb getFeeTypeInfoTb() {
		return this.feeTypeInfoTb;
	}

	public void setFeeTypeInfoTb(FeeTypeInfoTb feeTypeInfoTb) {
		this.feeTypeInfoTb = feeTypeInfoTb;
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

	public Double getFeePrice() {
		return this.feePrice;
	}

	public void setFeePrice(Double feePrice) {
		this.feePrice = feePrice;
	}

	public String getFeeEndDateJj() {
		return this.feeEndDateJj;
	}

	public void setFeeEndDateJj(String feeEndDateJj) {
		this.feeEndDateJj = feeEndDateJj;
	}

	public String getFeeEndDateGf() {
		return this.feeEndDateGf;
	}

	public void setFeeEndDateGf(String feeEndDateGf) {
		this.feeEndDateGf = feeEndDateGf;
	}

	public String getFeeRemark() {
		return this.feeRemark;
	}

	public void setFeeRemark(String feeRemark) {
		this.feeRemark = feeRemark;
	}

	public Integer getFeeStatus() {
		return this.feeStatus;
	}

	public void setFeeStatus(Integer feeStatus) {
		this.feeStatus = feeStatus;
	}

	public Integer getDjStatus() {
		return this.djStatus;
	}

	public void setDjStatus(Integer djStatus) {
		this.djStatus = djStatus;
	}

	public String getFeeJnDate() {
		return this.feeJnDate;
	}

	public void setFeeJnDate(String feeJnDate) {
		this.feeJnDate = feeJnDate;
	}

	public String getFeeUpZd() {
		return this.feeUpZd;
	}

	public void setFeeUpZd(String feeUpZd) {
		this.feeUpZd = feeUpZd;
	}

	public ZlajMainInfoTb getZlajMainInfoTb() {
		return zlajMainInfoTb;
	}

	public void setZlajMainInfoTb(ZlajMainInfoTb zlajMainInfoTb) {
		this.zlajMainInfoTb = zlajMainInfoTb;
	}

	public Double getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(Double feeRate) {
		this.feeRate = feeRate;
	}

	public String getTzsArea() {
		return tzsArea;
	}

	public void setTzsArea(String tzsArea) {
		this.tzsArea = tzsArea;
	}

	public Integer getYearFeeNo() {
		return yearFeeNo;
	}

	public void setYearFeeNo(Integer yearFeeNo) {
		this.yearFeeNo = yearFeeNo;
	}

	public String getFeeRange() {
		return feeRange;
	}

	public void setFeeRange(String feeRange) {
		this.feeRange = feeRange;
	}

	public Integer getAddStatus() {
		return addStatus;
	}

	public void setAddStatus(Integer addStatus) {
		this.addStatus = addStatus;
	}

}