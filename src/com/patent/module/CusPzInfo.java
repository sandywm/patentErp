package com.patent.module;

/**
 * CusPzInfo entity. @author MyEclipse Persistence Tools
 */

public class CusPzInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CusBackFeeInfo cusBackFeeInfo;
	private ZlajFeeInfoTb zlajFeeInfoTb;
	private Double pzPrice;
	private Double remainPrice;
	private String dealTime;

	// Constructors

	/** default constructor */
	public CusPzInfo() {
	}

	/** full constructor */
	public CusPzInfo(CusBackFeeInfo cusBackFeeInfo,
			ZlajFeeInfoTb zlajFeeInfoTb, Double pzPrice, Double remainPrice,String dealTime) {
		this.cusBackFeeInfo = cusBackFeeInfo;
		this.zlajFeeInfoTb = zlajFeeInfoTb;
		this.pzPrice = pzPrice;
		this.remainPrice = remainPrice;
		this.dealTime = dealTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CusBackFeeInfo getCusBackFeeInfo() {
		return this.cusBackFeeInfo;
	}

	public void setCusBackFeeInfo(CusBackFeeInfo cusBackFeeInfo) {
		this.cusBackFeeInfo = cusBackFeeInfo;
	}

	public ZlajFeeInfoTb getZlajFeeInfoTb() {
		return this.zlajFeeInfoTb;
	}

	public void setZlajFeeInfoTb(ZlajFeeInfoTb zlajFeeInfoTb) {
		this.zlajFeeInfoTb = zlajFeeInfoTb;
	}

	public Double getPzPrice() {
		return this.pzPrice;
	}

	public void setPzPrice(Double pzPrice) {
		this.pzPrice = pzPrice;
	}

	public String getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public Double getRemainPrice() {
		return remainPrice;
	}

	public void setRemainPrice(Double remainPrice) {
		this.remainPrice = remainPrice;
	}

}