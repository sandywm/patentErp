package com.patent.module;

import java.util.HashSet;
import java.util.Set;

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
	private String backDate;//退换时间
	private Double backFee;//退换费用总计
	private Integer backStatus;//退换状态
	private Double discountsFee;//优惠费用
	private String feeBatchNo;
	private String bankSerialNo;
	private String fpDate;
	private String fpNo;
	private Set<ZlajFeeSubInfoTb> zlajFeeSubInfoTbs = new HashSet<ZlajFeeSubInfoTb>();
	private Set<CusPzInfo> cpyPzInfos = new HashSet<CusPzInfo>();

	// Constructors

	/** default constructor */
	public ZlajFeeInfoTb() {
	}

	/** minimal constructor */
	public ZlajFeeInfoTb(FeeTypeInfoTb feeTypeInfoTb, CpyUserInfo cpyUserInfo,
			CpyInfoTb cpyInfoTb, ZlajMainInfoTb zlajMainInfoTb,Double feePrice, Double feeRate,String feeEndDateJj,
			String feeEndDateGf, String feeRemark, Integer feeStatus,
			Integer djStatus, String feeJnDate, String feeUpZd,String tzsArea,Integer yearFeeNo,String feeRange,Integer addStatus,
			String backDate,Double backFee,Integer backStatus,Double discountsFee,String feeBatchNo,String bankSerialNo,String fpDate,String fpNo) {
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
		this.backDate = backDate;
		this.backFee = backFee;
		this.backStatus = backStatus;
		this.discountsFee = discountsFee;
		this.feeBatchNo = feeBatchNo;
		this.bankSerialNo = bankSerialNo;
		this.fpDate = fpDate;
		this.fpNo = fpNo;
	}

	/** full constructor */
	public ZlajFeeInfoTb(Integer id,FeeTypeInfoTb feeTypeInfoTb, CpyUserInfo cpyUserInfo,
			CpyInfoTb cpyInfoTb, ZlajMainInfoTb zlajMainInfoTb,Double feePrice, Double feeRate,String feeEndDateJj,
			String feeEndDateGf, String feeRemark, Integer feeStatus,
			Integer djStatus, String feeJnDate, String feeUpZd,String tzsArea,Integer yearFeeNo,String feeRange,Integer addStatus,
			String backDate,Double backFee,Integer backStatus,Double discountsFee,String feeBatchNo,String bankSerialNo,String fpDate,String fpNo) {
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
		this.backDate = backDate;
		this.backFee = backFee;
		this.backStatus = backStatus;
		this.discountsFee = discountsFee;
		this.feeBatchNo = feeBatchNo;
		this.bankSerialNo = bankSerialNo;
		this.fpDate = fpDate;
		this.fpNo = fpNo;
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

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getFeeBatchNo() {
		return feeBatchNo;
	}

	public void setFeeBatchNo(String feeBatchNo) {
		this.feeBatchNo = feeBatchNo;
	}

	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	public Set<ZlajFeeSubInfoTb> getZlajFeeSubInfoTbs() {
		return zlajFeeSubInfoTbs;
	}

	public void setZlajFeeSubInfoTbs(Set<ZlajFeeSubInfoTb> zlajFeeSubInfoTbs) {
		this.zlajFeeSubInfoTbs = zlajFeeSubInfoTbs;
	}

	public Double getBackFee() {
		return backFee;
	}

	public void setBackFee(Double backFee) {
		this.backFee = backFee;
	}

	public Integer getBackStatus() {
		return backStatus;
	}

	public void setBackStatus(Integer backStatus) {
		this.backStatus = backStatus;
	}

	public Double getDiscountsFee() {
		return discountsFee;
	}

	public void setDiscountsFee(Double discountsFee) {
		this.discountsFee = discountsFee;
	}

	public String getFpDate() {
		return fpDate;
	}

	public void setFpDate(String fpDate) {
		this.fpDate = fpDate;
	}

	public String getFpNo() {
		return fpNo;
	}

	public void setFpNo(String fpNo) {
		this.fpNo = fpNo;
	}

	public Set<CusPzInfo> getCpyPzInfos() {
		return cpyPzInfos;
	}

	public void setCpyPzInfos(Set<CusPzInfo> cpyPzInfos) {
		this.cpyPzInfos = cpyPzInfos;
	}

}