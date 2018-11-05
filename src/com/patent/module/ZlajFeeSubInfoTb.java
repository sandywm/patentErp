package com.patent.module;

/**
 * ZlajFeeSubInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajFeeSubInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ZlajFeeInfoTb zlajFeeInfoTb;
	private FeeTypeInfoTb feeTypeInfoTb;
	private String feeRange;
	private Double feePrice;
	private String feeRemark;

	// Constructors

	/** default constructor */
	public ZlajFeeSubInfoTb() {
	}

	/** minimal constructor */
	public ZlajFeeSubInfoTb(ZlajFeeInfoTb zlajFeeInfoTb,
			FeeTypeInfoTb feeTypeInfoTb, String feeRange, Double feePrice,String feeRemark) {
		this.zlajFeeInfoTb = zlajFeeInfoTb;
		this.feeTypeInfoTb = feeTypeInfoTb;
		this.feeRange = feeRange;
		this.feePrice = feePrice;
		this.feeRemark = feeRemark;
	}

	/** full constructor */
	public ZlajFeeSubInfoTb(Integer id,ZlajFeeInfoTb zlajFeeInfoTb,
			FeeTypeInfoTb feeTypeInfoTb, String feeRange, Double feePrice,
			String feeRemark) {
		this.id = id;
		this.zlajFeeInfoTb = zlajFeeInfoTb;
		this.feeTypeInfoTb = feeTypeInfoTb;
		this.feeRange = feeRange;
		this.feePrice = feePrice;
		this.feeRemark = feeRemark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZlajFeeInfoTb getZlajFeeInfoTb() {
		return this.zlajFeeInfoTb;
	}

	public void setZlajFeeInfoTb(ZlajFeeInfoTb zlajFeeInfoTb) {
		this.zlajFeeInfoTb = zlajFeeInfoTb;
	}

	public FeeTypeInfoTb getFeeTypeInfoTb() {
		return this.feeTypeInfoTb;
	}

	public void setFeeTypeInfoTb(FeeTypeInfoTb feeTypeInfoTb) {
		this.feeTypeInfoTb = feeTypeInfoTb;
	}

	public String getFeeRange() {
		return this.feeRange;
	}

	public void setFeeRange(String feeRange) {
		this.feeRange = feeRange;
	}

	public Double getFeePrice() {
		return this.feePrice;
	}

	public void setFeePrice(Double feePrice) {
		this.feePrice = feePrice;
	}

	public String getFeeRemark() {
		return this.feeRemark;
	}

	public void setFeeRemark(String feeRemark) {
		this.feeRemark = feeRemark;
	}

}