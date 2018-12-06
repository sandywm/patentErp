package com.patent.module;

/**
 * CpyBonusInfo entity. @author MyEclipse Persistence Tools
 */

public class CpyBonusInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private String zlType;
	private Integer zlLevel;
	private String bonusFee;
	private String workPosition;

	// Constructors

	/** default constructor */
	public CpyBonusInfo() {
	}

	public CpyBonusInfo(CpyInfoTb cpyInfoTb, String zlType, Integer zlLevel,
			String bonusFee, String workPosition) {
		this.cpyInfoTb = cpyInfoTb;
		this.zlType = zlType;
		this.zlLevel = zlLevel;
		this.bonusFee = bonusFee;
		this.workPosition = workPosition;
	}
	
	/** full constructor */
	public CpyBonusInfo(Integer id,CpyInfoTb cpyInfoTb, String zlType, Integer zlLevel,
			String bonusFee, String workPosition) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.zlType = zlType;
		this.zlLevel = zlLevel;
		this.bonusFee = bonusFee;
		this.workPosition = workPosition;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CpyInfoTb getCpyInfoTb() {
		return this.cpyInfoTb;
	}

	public void setCpyInfoTb(CpyInfoTb cpyInfoTb) {
		this.cpyInfoTb = cpyInfoTb;
	}

	public String getZlType() {
		return this.zlType;
	}

	public void setZlType(String zlType) {
		this.zlType = zlType;
	}

	public Integer getZlLevel() {
		return this.zlLevel;
	}

	public void setZlLevel(Integer zlLevel) {
		this.zlLevel = zlLevel;
	}

	public String getBonusFee() {
		return this.bonusFee;
	}

	public void setBonusFee(String bonusFee) {
		this.bonusFee = bonusFee;
	}

	public String getWorkPosition() {
		return this.workPosition;
	}

	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}

}