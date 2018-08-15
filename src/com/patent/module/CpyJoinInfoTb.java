package com.patent.module;

/**
 * CpyJoinInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CpyJoinInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private Integer parCpyId;
	private Integer subCpyId;
	private String parCpyName;
	private String subCpyName;
	private Integer joinStatus;
	private String applyContent;
	private String applyDate;
	private String czDate;
	private String czContent;

	// Constructors

	/** default constructor */
	public CpyJoinInfoTb() {
	}

	/** minimal constructor */
	public CpyJoinInfoTb(CpyInfoTb cpyInfoTb, Integer parCpyId,
			Integer subCpyId, String parCpyName, String subCpyName,
			Integer joinStatus, String applyContent, String applyDate,
			String czDate, String czContent) {
		this.cpyInfoTb = cpyInfoTb;
		this.parCpyId = parCpyId;
		this.subCpyId = subCpyId;
		this.parCpyName = parCpyName;
		this.subCpyName = subCpyName;
		this.joinStatus = joinStatus;
		this.applyContent = applyContent;
		this.applyDate = applyDate;
		this.czDate = czDate;
		this.czContent = czContent;
	}

	/** full constructor */
	public CpyJoinInfoTb(Integer id,CpyInfoTb cpyInfoTb, Integer parCpyId,
			Integer subCpyId, String parCpyName, String subCpyName,
			Integer joinStatus, String applyContent, String applyDate,
			String czDate, String czContent) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.parCpyId = parCpyId;
		this.subCpyId = subCpyId;
		this.parCpyName = parCpyName;
		this.subCpyName = subCpyName;
		this.joinStatus = joinStatus;
		this.applyContent = applyContent;
		this.applyDate = applyDate;
		this.czDate = czDate;
		this.czContent = czContent;
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

	public Integer getParCpyId() {
		return this.parCpyId;
	}

	public void setParCpyId(Integer parCpyId) {
		this.parCpyId = parCpyId;
	}

	public Integer getSubCpyId() {
		return this.subCpyId;
	}

	public void setSubCpyId(Integer subCpyId) {
		this.subCpyId = subCpyId;
	}

	public String getParCpyName() {
		return this.parCpyName;
	}

	public void setParCpyName(String parCpyName) {
		this.parCpyName = parCpyName;
	}

	public String getSubCpyName() {
		return this.subCpyName;
	}

	public void setSubCpyName(String subCpyName) {
		this.subCpyName = subCpyName;
	}

	public Integer getJoinStatus() {
		return this.joinStatus;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

	public String getApplyContent() {
		return this.applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCzDate() {
		return this.czDate;
	}

	public void setCzDate(String czDate) {
		this.czDate = czDate;
	}

	public String getCzContent() {
		return this.czContent;
	}

	public void setCzContent(String czContent) {
		this.czContent = czContent;
	}

}