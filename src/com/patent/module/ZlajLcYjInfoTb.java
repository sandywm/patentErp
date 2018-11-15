package com.patent.module;

/**
 * ZlajLcYjInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajLcYjInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ZlajLcMxInfoTb lcmx;
	private CpyUserInfo user;
	private CpyInfoTb cpy;
	private String applyDate;
	private String applyCause;
	private Integer checkStatus;
	private String checkDate;
	private Integer checkUserId;

	// Constructors

	/** default constructor */
	public ZlajLcYjInfoTb() {
	}

	

	/** minimal constructor */
	public ZlajLcYjInfoTb(ZlajLcMxInfoTb lcmx, CpyUserInfo user,
			CpyInfoTb cpy, String applyDate, String applyCause,
			Integer checkStatus, String checkDate, Integer checkUserId) {
		this.lcmx = lcmx;
		this.user = user;
		this.cpy = cpy;
		this.applyDate = applyDate;
		this.applyCause = applyCause;
		this.checkStatus = checkStatus;
		this.checkDate = checkDate;
		this.checkUserId = checkUserId;
	}

	/** full constructor */
	public ZlajLcYjInfoTb(Integer id, ZlajLcMxInfoTb lcmx, CpyUserInfo user,
			CpyInfoTb cpy, String applyDate, String applyCause,
			Integer checkStatus, String checkDate, Integer checkUserId) {
		this.id = id;
		this.lcmx = lcmx;
		this.user = user;
		this.cpy = cpy;
		this.applyDate = applyDate;
		this.applyCause = applyCause;
		this.checkStatus = checkStatus;
		this.checkDate = checkDate;
		this.checkUserId = checkUserId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public ZlajLcMxInfoTb getLcmx() {
		return lcmx;
	}



	public void setLcmx(ZlajLcMxInfoTb lcmx) {
		this.lcmx = lcmx;
	}



	public CpyUserInfo getUser() {
		return user;
	}



	public void setUser(CpyUserInfo user) {
		this.user = user;
	}



	public CpyInfoTb getCpy() {
		return cpy;
	}



	public void setCpy(CpyInfoTb cpy) {
		this.cpy = cpy;
	}



	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyCause() {
		return this.applyCause;
	}

	public void setApplyCause(String applyCause) {
		this.applyCause = applyCause;
	}

	public Integer getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getCheckUserId() {
		return this.checkUserId;
	}

	public void setCheckUserId(Integer checkUserId) {
		this.checkUserId = checkUserId;
	}

}