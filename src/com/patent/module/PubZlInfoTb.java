package com.patent.module;

/**
 * PubZlInfoTb entity. @author MyEclipse Persistence Tools
 */

public class PubZlInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ApplyInfoTb applyInfoTb;
	private String zlTitle;
	private String zlContent;
	private String zlNo;
	private String zlType;
	private String zlUpCl;
	private String zlNewDate;
	private Integer zlStatus;
	private Integer lqUserId;
	private String lqUserName;
	private Integer lqCpyId;
	private String lqCpyName;
	private String lqDate;
	private Integer ajId;

	// Constructors

	/** default constructor */
	public PubZlInfoTb() {
	}

	public PubZlInfoTb(ApplyInfoTb applyInfoTb, String zlTitle, String zlContent,String zlNo,String zlType,
			String zlUpCl, String zlNewDate, Integer zlStatus, Integer lqUserId,
			String lqUserName, Integer lqCpyId, String lqCpyName, String lqDate,
			Integer ajId) {
		this.applyInfoTb = applyInfoTb;
		this.zlTitle = zlTitle;
		this.zlContent = zlContent;
		this.zlNo = zlNo;
		this.zlType = zlType;
		this.zlUpCl = zlUpCl;
		this.zlNewDate = zlNewDate;
		this.zlStatus = zlStatus;
		this.lqUserId = lqUserId;
		this.lqUserName = lqUserName;
		this.lqCpyId = lqCpyId;
		this.lqCpyName = lqCpyName;
		this.lqDate = lqDate;
		this.ajId = ajId;
	}
	
	/** full constructor */
	public PubZlInfoTb(Integer id,ApplyInfoTb applyInfoTb, String zlTitle, String zlContent,String zlNo,String zlType,
			String zlUpCl, String zlNewDate, Integer zlStatus, Integer lqUserId,
			String lqUserName, Integer lqCpyId, String lqCpyName, String lqDate,
			Integer ajId) {
		this.id = id;
		this.applyInfoTb = applyInfoTb;
		this.zlTitle = zlTitle;
		this.zlContent = zlContent;
		this.zlNo = zlNo;
		this.zlType = zlType;
		this.zlUpCl = zlUpCl;
		this.zlNewDate = zlNewDate;
		this.zlStatus = zlStatus;
		this.lqUserId = lqUserId;
		this.lqUserName = lqUserName;
		this.lqCpyId = lqCpyId;
		this.lqCpyName = lqCpyName;
		this.lqDate = lqDate;
		this.ajId = ajId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ApplyInfoTb getApplyInfoTb() {
		return this.applyInfoTb;
	}

	public void setApplyInfoTb(ApplyInfoTb applyInfoTb) {
		this.applyInfoTb = applyInfoTb;
	}

	public String getZlTitle() {
		return this.zlTitle;
	}

	public void setZlTitle(String zlTitle) {
		this.zlTitle = zlTitle;
	}

	public String getZlType() {
		return this.zlType;
	}

	public void setZlType(String zlType) {
		this.zlType = zlType;
	}

	public String getZlUpCl() {
		return this.zlUpCl;
	}

	public void setZlUpCl(String zlUpCl) {
		this.zlUpCl = zlUpCl;
	}

	public String getZlNewDate() {
		return this.zlNewDate;
	}

	public void setZlNewDate(String zlNewDate) {
		this.zlNewDate = zlNewDate;
	}

	public Integer getZlStatus() {
		return this.zlStatus;
	}

	public void setZlStatus(Integer zlStatus) {
		this.zlStatus = zlStatus;
	}

	public Integer getLqUserId() {
		return this.lqUserId;
	}

	public void setLqUserId(Integer lqUserId) {
		this.lqUserId = lqUserId;
	}

	public String getLqUserName() {
		return this.lqUserName;
	}

	public void setLqUserName(String lqUserName) {
		this.lqUserName = lqUserName;
	}

	public Integer getLqCpyId() {
		return this.lqCpyId;
	}

	public void setLqCpyId(Integer lqCpyId) {
		this.lqCpyId = lqCpyId;
	}

	public String getLqCpyName() {
		return this.lqCpyName;
	}

	public void setLqCpyName(String lqCpyName) {
		this.lqCpyName = lqCpyName;
	}

	public String getLqDate() {
		return this.lqDate;
	}

	public void setLqDate(String lqDate) {
		this.lqDate = lqDate;
	}

	public Integer getAjId() {
		return this.ajId;
	}

	public void setAjId(Integer ajId) {
		this.ajId = ajId;
	}

	public String getZlNo() {
		return zlNo;
	}

	public void setZlNo(String zlNo) {
		this.zlNo = zlNo;
	}

	public String getZlContent() {
		return zlContent;
	}

	public void setZlContent(String zlContent) {
		this.zlContent = zlContent;
	}

}