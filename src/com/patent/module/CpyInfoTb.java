package com.patent.module;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CpyInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CpyInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String cpyName;
	private String cpyAddress;
	private String cpyProv;
	private String cpyCity;
	private String cpyFr;
	private String cpyYyzz;
	private String cpyLxr;
	private String lxrTel;
	private String lxrEmail;
	private String cpySubId;
	private Integer cpyParId;
	private String cpyUrl;
	private String cpyProfile;
	private Date signDate;
	private Date endDate;
	private Integer hotStatus;
	private Integer cpyLevel;
	private Set<CpyRoleInfoTb> cpyRoleInfoTbs = new HashSet<CpyRoleInfoTb>();
	private Set<JsFiledInfoTb> jsFiledInfoTbs = new HashSet<JsFiledInfoTb>();
	private Set<ZlajFeeInfoTb> zlajFeeInfoTbs = new HashSet<ZlajFeeInfoTb>();
	private Set<CpyUserInfo> cpyUserInfos = new HashSet<CpyUserInfo>();
	private Set<ZlajMainInfoTb> zlajMainInfoTbs = new HashSet<ZlajMainInfoTb>();

	// Constructors

	/** default constructor */
	public CpyInfoTb() {
	}

	/** minimal constructor */
	public CpyInfoTb(String cpyName, String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyYyzz, String cpyLxr,
			String lxrTel, String cpySubId, Integer cpyParId, Date signDate,
			Date endDate, Integer hotStatus, Integer cpyLevel) {
		this.cpyName = cpyName;
		this.cpyAddress = cpyAddress;
		this.cpyProv = cpyProv;
		this.cpyCity = cpyCity;
		this.cpyFr = cpyFr;
		this.cpyYyzz = cpyYyzz;
		this.cpyLxr = cpyLxr;
		this.lxrTel = lxrTel;
		this.cpySubId = cpySubId;
		this.cpyParId = cpyParId;
		this.signDate = signDate;
		this.endDate = endDate;
		this.hotStatus = hotStatus;
		this.cpyLevel = cpyLevel;
	}

	/** full constructor */
	public CpyInfoTb(Integer id,String cpyName, String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyYyzz, String cpyLxr,
			String lxrTel, String lxrEmail, String cpySubId, Integer cpyParId,
			String cpyUrl, String cpyProfile, Date signDate, Date endDate,
			Integer hotStatus, Integer cpyLevel) {
		this.id = id;
		this.cpyName = cpyName;
		this.cpyAddress = cpyAddress;
		this.cpyProv = cpyProv;
		this.cpyCity = cpyCity;
		this.cpyFr = cpyFr;
		this.cpyYyzz = cpyYyzz;
		this.cpyLxr = cpyLxr;
		this.lxrTel = lxrTel;
		this.lxrEmail = lxrEmail;
		this.cpySubId = cpySubId;
		this.cpyParId = cpyParId;
		this.cpyUrl = cpyUrl;
		this.cpyProfile = cpyProfile;
		this.signDate = signDate;
		this.endDate = endDate;
		this.hotStatus = hotStatus;
		this.cpyLevel = cpyLevel;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpyName() {
		return this.cpyName;
	}

	public void setCpyName(String cpyName) {
		this.cpyName = cpyName;
	}

	public String getCpyAddress() {
		return this.cpyAddress;
	}

	public void setCpyAddress(String cpyAddress) {
		this.cpyAddress = cpyAddress;
	}

	public String getCpyProv() {
		return this.cpyProv;
	}

	public void setCpyProv(String cpyProv) {
		this.cpyProv = cpyProv;
	}

	public String getCpyCity() {
		return this.cpyCity;
	}

	public void setCpyCity(String cpyCity) {
		this.cpyCity = cpyCity;
	}

	public String getCpyFr() {
		return this.cpyFr;
	}

	public void setCpyFr(String cpyFr) {
		this.cpyFr = cpyFr;
	}

	public String getCpyYyzz() {
		return this.cpyYyzz;
	}

	public void setCpyYyzz(String cpyYyzz) {
		this.cpyYyzz = cpyYyzz;
	}

	public String getCpyLxr() {
		return this.cpyLxr;
	}

	public void setCpyLxr(String cpyLxr) {
		this.cpyLxr = cpyLxr;
	}

	public String getLxrTel() {
		return this.lxrTel;
	}

	public void setLxrTel(String lxrTel) {
		this.lxrTel = lxrTel;
	}

	public String getLxrEmail() {
		return this.lxrEmail;
	}

	public void setLxrEmail(String lxrEmail) {
		this.lxrEmail = lxrEmail;
	}

	public String getCpySubId() {
		return this.cpySubId;
	}

	public void setCpySubId(String cpySubId) {
		this.cpySubId = cpySubId;
	}

	public Integer getCpyParId() {
		return this.cpyParId;
	}

	public void setCpyParId(Integer cpyParId) {
		this.cpyParId = cpyParId;
	}

	public String getCpyUrl() {
		return this.cpyUrl;
	}

	public void setCpyUrl(String cpyUrl) {
		this.cpyUrl = cpyUrl;
	}

	public String getCpyProfile() {
		return this.cpyProfile;
	}

	public void setCpyProfile(String cpyProfile) {
		this.cpyProfile = cpyProfile;
	}

	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getHotStatus() {
		return this.hotStatus;
	}

	public void setHotStatus(Integer hotStatus) {
		this.hotStatus = hotStatus;
	}

	public Integer getCpyLevel() {
		return this.cpyLevel;
	}

	public void setCpyLevel(Integer cpyLevel) {
		this.cpyLevel = cpyLevel;
	}

	public Set<CpyRoleInfoTb> getCpyRoleInfoTbs() {
		return cpyRoleInfoTbs;
	}

	public void setCpyRoleInfoTbs(Set<CpyRoleInfoTb> cpyRoleInfoTbs) {
		this.cpyRoleInfoTbs = cpyRoleInfoTbs;
	}

	public Set<JsFiledInfoTb> getJsFiledInfoTbs() {
		return jsFiledInfoTbs;
	}

	public void setJsFiledInfoTbs(Set<JsFiledInfoTb> jsFiledInfoTbs) {
		this.jsFiledInfoTbs = jsFiledInfoTbs;
	}

	public Set<ZlajFeeInfoTb> getZlajFeeInfoTbs() {
		return zlajFeeInfoTbs;
	}

	public void setZlajFeeInfoTbs(Set<ZlajFeeInfoTb> zlajFeeInfoTbs) {
		this.zlajFeeInfoTbs = zlajFeeInfoTbs;
	}

	public Set<CpyUserInfo> getCpyUserInfos() {
		return cpyUserInfos;
	}

	public void setCpyUserInfos(Set<CpyUserInfo> cpyUserInfos) {
		this.cpyUserInfos = cpyUserInfos;
	}

	public Set<ZlajMainInfoTb> getZlajMainInfoTbs() {
		return zlajMainInfoTbs;
	}

	public void setZlajMainInfoTbs(Set<ZlajMainInfoTb> zlajMainInfoTbs) {
		this.zlajMainInfoTbs = zlajMainInfoTbs;
	}



}