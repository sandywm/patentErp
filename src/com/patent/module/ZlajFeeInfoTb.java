package com.patent.module;

import java.util.Date;

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
	private Double feePrice;
	private Date feeEndDateJj;
	private Date feeEndDateGf;
	private String feeRemark;
	private Integer feeStatus;
	private Integer djStatus;
	private Date feeJnDate;
	private String feeUpZd;

	// Constructors

	/** default constructor */
	public ZlajFeeInfoTb() {
	}

	/** minimal constructor */
	public ZlajFeeInfoTb(FeeTypeInfoTb feeTypeInfoTb, CpyUserInfo cpyUserInfo,
			CpyInfoTb cpyInfoTb, Double feePrice, Date feeEndDateJj,
			Date feeEndDateGf, String feeRemark, Integer feeStatus,
			Integer djStatus, Date feeJnDate, String feeUpZd) {
		this.feeTypeInfoTb = feeTypeInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.feePrice = feePrice;
		this.feeEndDateJj = feeEndDateJj;
		this.feeEndDateGf = feeEndDateGf;
		this.feeRemark = feeRemark;
		this.feeStatus = feeStatus;
		this.djStatus = djStatus;
		this.feeJnDate = feeJnDate;
		this.feeUpZd = feeUpZd;
	}

	/** full constructor */
	public ZlajFeeInfoTb(Integer id,FeeTypeInfoTb feeTypeInfoTb, CpyUserInfo cpyUserInfo,
			CpyInfoTb cpyInfoTb, Double feePrice, Date feeEndDateJj,
			Date feeEndDateGf, String feeRemark, Integer feeStatus,
			Integer djStatus, Date feeJnDate, String feeUpZd) {
		this.id = id;
		this.feeTypeInfoTb = feeTypeInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.feePrice = feePrice;
		this.feeEndDateJj = feeEndDateJj;
		this.feeEndDateGf = feeEndDateGf;
		this.feeRemark = feeRemark;
		this.feeStatus = feeStatus;
		this.djStatus = djStatus;
		this.feeJnDate = feeJnDate;
		this.feeUpZd = feeUpZd;
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

	public Date getFeeEndDateJj() {
		return this.feeEndDateJj;
	}

	public void setFeeEndDateJj(Date feeEndDateJj) {
		this.feeEndDateJj = feeEndDateJj;
	}

	public Date getFeeEndDateGf() {
		return this.feeEndDateGf;
	}

	public void setFeeEndDateGf(Date feeEndDateGf) {
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

	public Date getFeeJnDate() {
		return this.feeJnDate;
	}

	public void setFeeJnDate(Date feeJnDate) {
		this.feeJnDate = feeJnDate;
	}

	public String getFeeUpZd() {
		return this.feeUpZd;
	}

	public void setFeeUpZd(String feeUpZd) {
		this.feeUpZd = feeUpZd;
	}

}