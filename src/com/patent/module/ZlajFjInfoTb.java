package com.patent.module;

import java.util.Date;

/**
 * ZlajFjInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajFjInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyUserInfo cpyUserInfo;
	private ZlajMainInfoTb zlajMainInfoTb;
	private String fjName;
	private String fjVersion;
	private String fjType;
	private String fjGs;
	private String fjDx;
	private Date fjUpDate;

	// Constructors

	/** default constructor */
	public ZlajFjInfoTb() {
	}

	/** minimal constructor */
	public ZlajFjInfoTb(CpyUserInfo cpyUserInfo, ZlajMainInfoTb zlajMainInfoTb,
			String fjName, String fjVersion, String fjType, String fjGs,
			String fjDx, Date fjUpDate) {
		this.cpyUserInfo = cpyUserInfo;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.fjName = fjName;
		this.fjVersion = fjVersion;
		this.fjType = fjType;
		this.fjGs = fjGs;
		this.fjDx = fjDx;
		this.fjUpDate = fjUpDate;
	}

	/** full constructor */
	public ZlajFjInfoTb(Integer id,CpyUserInfo cpyUserInfo, ZlajMainInfoTb zlajMainInfoTb,
			String fjName, String fjVersion, String fjType, String fjGs,
			String fjDx, Date fjUpDate) {
		this.id = id;
		this.cpyUserInfo = cpyUserInfo;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.fjName = fjName;
		this.fjVersion = fjVersion;
		this.fjType = fjType;
		this.fjGs = fjGs;
		this.fjDx = fjDx;
		this.fjUpDate = fjUpDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getFjName() {
		return this.fjName;
	}

	public void setFjName(String fjName) {
		this.fjName = fjName;
	}

	public String getFjVersion() {
		return this.fjVersion;
	}

	public void setFjVersion(String fjVersion) {
		this.fjVersion = fjVersion;
	}

	public String getFjType() {
		return this.fjType;
	}

	public void setFjType(String fjType) {
		this.fjType = fjType;
	}

	public String getFjGs() {
		return this.fjGs;
	}

	public void setFjGs(String fjGs) {
		this.fjGs = fjGs;
	}

	public String getFjDx() {
		return this.fjDx;
	}

	public void setFjDx(String fjDx) {
		this.fjDx = fjDx;
	}

	public Date getFjUpDate() {
		return this.fjUpDate;
	}

	public void setFjUpDate(Date fjUpDate) {
		this.fjUpDate = fjUpDate;
	}

}