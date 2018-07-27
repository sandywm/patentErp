package com.patent.module;

/**
 * ZlajEwyqInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajEwyqInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String yqContent;
	private String yqType;

	// Constructors

	/** default constructor */
	public ZlajEwyqInfoTb() {
	}

	/** full constructor */
	public ZlajEwyqInfoTb(String yqContent, String yqType) {
		this.yqContent = yqContent;
		this.yqType = yqType;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYqContent() {
		return this.yqContent;
	}

	public void setYqContent(String yqContent) {
		this.yqContent = yqContent;
	}

	public String getYqType() {
		return this.yqType;
	}

	public void setYqType(String yqType) {
		this.yqType = yqType;
	}

}