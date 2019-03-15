package com.patent.module;

/**
 * ZlajZdSubmitTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajZdSubmitTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ZlajQqsInfoTb zlajQqsInfoTb;
	private CpyUserInfo cpyUserInfo;
	private ZlajMainInfoTb zlajMainInfoTb;
	private String qqsFjPath;
	private String uploadDate;
	private CpyInfoTb cpyInfoTb;
	private Integer signStatus;

	// Constructors

	/** default constructor */
	public ZlajZdSubmitTb() {
	}

	/** full constructor */
	public ZlajZdSubmitTb(ZlajQqsInfoTb zlajQqsInfoTb, CpyUserInfo cpyUserInfo,
			ZlajMainInfoTb zlajMainInfoTb, String qqsFjPath, String uploadDate,
			CpyInfoTb cpyInfoTb, Integer signStatus) {
		this.zlajQqsInfoTb = zlajQqsInfoTb;
		this.cpyUserInfo = cpyUserInfo;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.qqsFjPath = qqsFjPath;
		this.uploadDate = uploadDate;
		this.cpyInfoTb = cpyInfoTb;
		this.signStatus = signStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZlajQqsInfoTb getZlajQqsInfoTb() {
		return this.zlajQqsInfoTb;
	}

	public void setZlajQqsInfoTb(ZlajQqsInfoTb zlajQqsInfoTb) {
		this.zlajQqsInfoTb = zlajQqsInfoTb;
	}

	public CpyUserInfo getCpyUserInfo() {
		return this.cpyUserInfo;
	}

	public void setCpyUserInfo(CpyUserInfo cpyUserInfo) {
		this.cpyUserInfo = cpyUserInfo;
	}

	public ZlajMainInfoTb getZlajMainInfoTb() {
		return this.zlajMainInfoTb;
	}

	public void setZlajMainInfoTb(ZlajMainInfoTb zlajMainInfoTb) {
		this.zlajMainInfoTb = zlajMainInfoTb;
	}

	public String getQqsFjPath() {
		return this.qqsFjPath;
	}

	public void setQqsFjPath(String qqsFjPath) {
		this.qqsFjPath = qqsFjPath;
	}

	public String getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}


	public CpyInfoTb getCpyInfoTb() {
		return cpyInfoTb;
	}

	public void setCpyInfoTb(CpyInfoTb cpyInfoTb) {
		this.cpyInfoTb = cpyInfoTb;
	}

	public Integer getSignStatus() {
		return this.signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

}