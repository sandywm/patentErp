package com.patent.module;

/**
 * PubZlCzRecordTb entity. @author MyEclipse Persistence Tools
 */

public class PubZlCzRecordTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private PubZlInfoTb pubZlInfoTb;
	private String addDate;
	private String addContent;

	// Constructors

	/** default constructor */
	public PubZlCzRecordTb() {
	}

	/** full constructor */
	public PubZlCzRecordTb(PubZlInfoTb pubZlInfoTb, String addDate,
			String addContent) {
		this.pubZlInfoTb = pubZlInfoTb;
		this.addDate = addDate;
		this.addContent = addContent;
	}

	public PubZlCzRecordTb(Integer id,PubZlInfoTb pubZlInfoTb, String addDate,
			String addContent) {
		this.id = id;
		this.pubZlInfoTb = pubZlInfoTb;
		this.addDate = addDate;
		this.addContent = addContent;
	}
	
	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PubZlInfoTb getPubZlInfoTb() {
		return this.pubZlInfoTb;
	}

	public void setPubZlInfoTb(PubZlInfoTb pubZlInfoTb) {
		this.pubZlInfoTb = pubZlInfoTb;
	}

	public String getAddDate() {
		return this.addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getAddContent() {
		return this.addContent;
	}

	public void setAddContent(String addContent) {
		this.addContent = addContent;
	}

}