package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * CusBackFeeInfo entity. @author MyEclipse Persistence Tools
 */

public class CusBackFeeInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CustomerInfoTb customerInfoTb;
	private CpyUserInfo cpyUserInfo;
	private CpyInfoTb cpyInfoTb;
	private String backFeePrice;
	private String backDate;
	private String backType;
	private String operateTime;
	private String remark;
	private Set<CusPzInfo> cusPzInfos = new HashSet<CusPzInfo>();

	// Constructors

	/** default constructor */
	public CusBackFeeInfo() {
	}

	/** minimal constructor */
	public CusBackFeeInfo(CustomerInfoTb customerInfoTb,
			CpyUserInfo cpyUserInfo, CpyInfoTb cpyInfoTb, String backFeePrice,
			String backDate, String backType, String operateTime,
			String remark) {
		this.customerInfoTb = customerInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.backFeePrice = backFeePrice;
		this.backDate = backDate;
		this.backType = backType;
		this.operateTime = operateTime;
		this.remark = remark;
	}

	/** full constructor */
	public CusBackFeeInfo(Integer id,CustomerInfoTb customerInfoTb,
			CpyUserInfo cpyUserInfo, CpyInfoTb cpyInfoTb, String backFeePrice,
			String backDate, String backType, String operateTime,
			String remark) {
		this.id = id;
		this.customerInfoTb = customerInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.cpyInfoTb = cpyInfoTb;
		this.backFeePrice = backFeePrice;
		this.backDate = backDate;
		this.backType = backType;
		this.operateTime = operateTime;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerInfoTb getCustomerInfoTb() {
		return this.customerInfoTb;
	}

	public void setCustomerInfoTb(CustomerInfoTb customerInfoTb) {
		this.customerInfoTb = customerInfoTb;
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

	public String getBackFeePrice() {
		return this.backFeePrice;
	}

	public void setBackFeePrice(String backFeePrice) {
		this.backFeePrice = backFeePrice;
	}

	public String getBackDate() {
		return this.backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getBackType() {
		return this.backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<CusPzInfo> getCusPzInfos() {
		return this.cusPzInfos;
	}

	public void setCusPzInfos(Set<CusPzInfo> cusPzInfos) {
		this.cusPzInfos = cusPzInfos;
	}

}