package com.patent.module;

/**
 * JsFiledInfoTb entity. @author MyEclipse Persistence Tools
 */

public class JsFiledInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private String zyName;

	// Constructors

	/** default constructor */
	public JsFiledInfoTb() {
	}

	/** full constructor */
	public JsFiledInfoTb(CpyInfoTb cpyInfoTb, String zyName) {
		this.cpyInfoTb = cpyInfoTb;
		this.zyName = zyName;
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

	public String getZyName() {
		return this.zyName;
	}

	public void setZyName(String zyName) {
		this.zyName = zyName;
	}

}