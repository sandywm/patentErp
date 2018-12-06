package com.patent.module;

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
	private String cpyNamePy;
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
	private String signDate;
	private String endDate;
	private Integer hotStatus;
	private Integer cpyLevel;
	private Integer zlNum;
	private String bankAccountName;
	private String bankName;
	private String bankNo;
	private String saleBonus;
	private String dlFeeFm;
	private String dlFeeXx;
	private String dlFeeWg;
	
	private Set<CpyRoleInfoTb> cpyRoleInfoTbs = new HashSet<CpyRoleInfoTb>();
	private Set<JsFiledInfoTb> jsFiledInfoTbs = new HashSet<JsFiledInfoTb>();
	private Set<ZlajFeeInfoTb> zlajFeeInfoTbs = new HashSet<ZlajFeeInfoTb>();
	private Set<CpyUserInfo> cpyUserInfos = new HashSet<CpyUserInfo>();
	private Set<ZlajMainInfoTb> zlajMainInfoTbs = new HashSet<ZlajMainInfoTb>();
	private Set<CpyJoinInfoTb> cpyJoinInfoTbs = new HashSet<CpyJoinInfoTb>();
	private Set<CustomerInfoTb> customerInfoTbs = new HashSet<CustomerInfoTb>();
	private Set<ZlajLcYjInfoTb> lcyjs = new HashSet<ZlajLcYjInfoTb>();
	// Constructors

	/** default constructor */
	public CpyInfoTb() {
	}

	/** minimal constructor */
	public CpyInfoTb(String cpyName, String cpyNamePy,String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyYyzz, String cpyLxr,
			String lxrTel, String lxrEmail, String cpySubId, Integer cpyParId,
			String cpyUrl, String cpyProfile, String signDate, String endDate,
			Integer hotStatus, Integer cpyLevel,Integer zlNum,String bankAccountName,
			String bankName,String bankNo,String saleBonus,String dlFeeFm,String dlFeeXx,String dlFeeWg) {
		this.cpyName = cpyName;
		this.cpyNamePy = cpyNamePy;
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
		this.zlNum = zlNum;
	}

	/** full constructor */
	public CpyInfoTb(Integer id, String cpyName, String cpyNamePy,String cpyAddress, String cpyProv,
			String cpyCity, String cpyFr, String cpyYyzz, String cpyLxr,
			String lxrTel, String lxrEmail, String cpySubId, Integer cpyParId,
			String cpyUrl, String cpyProfile, String signDate, String endDate,
			Integer hotStatus, Integer cpyLevel,Integer zlNum,String bankAccountName,
			String bankName,String bankNo,String saleBonus,String dlFeeFm,String dlFeeXx,String dlFeeWg) {
		this.id = id;
		this.cpyName = cpyName;
		this.cpyNamePy = cpyNamePy;
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
		this.zlNum = zlNum;
		this.bankAccountName = bankAccountName;
		this.bankName = bankName;
		this.bankNo = bankNo;
		this.saleBonus = saleBonus;
		this.dlFeeFm = dlFeeFm;
		this.dlFeeXx = dlFeeXx;
		this.dlFeeWg = dlFeeWg;
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

	public String getSignDate() {
		return this.signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
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

	public String getCpyNamePy() {
		return cpyNamePy;
	}

	public void setCpyNamePy(String cpyNamePy) {
		this.cpyNamePy = cpyNamePy;
	}

	public Set<CpyJoinInfoTb> getCpyJoinInfoTbs() {
		return cpyJoinInfoTbs;
	}

	public void setCpyJoinInfoTbs(Set<CpyJoinInfoTb> cpyJoinInfoTbs) {
		this.cpyJoinInfoTbs = cpyJoinInfoTbs;
	}

	public Integer getZlNum() {
		return zlNum;
	}

	public void setZlNum(Integer zlNum) {
		this.zlNum = zlNum;
	}

	public Set<CustomerInfoTb> getCustomerInfoTbs() {
		return customerInfoTbs;
	}

	public void setCustomerInfoTbs(Set<CustomerInfoTb> customerInfoTbs) {
		this.customerInfoTbs = customerInfoTbs;
	}
	public Set<ZlajLcYjInfoTb> getLcyjs() {
		return lcyjs;
	}

	public void setLcyjs(Set<ZlajLcYjInfoTb> lcyjs) {
		this.lcyjs = lcyjs;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getSaleBonus() {
		return saleBonus;
	}

	public void setSaleBonus(String saleBonus) {
		this.saleBonus = saleBonus;
	}

	public String getDlFeeFm() {
		return dlFeeFm;
	}

	public void setDlFeeFm(String dlFeeFm) {
		this.dlFeeFm = dlFeeFm;
	}

	public String getDlFeeXx() {
		return dlFeeXx;
	}

	public void setDlFeeXx(String dlFeeXx) {
		this.dlFeeXx = dlFeeXx;
	}

	public String getDlFeeWg() {
		return dlFeeWg;
	}

	public void setDlFeeWg(String dlFeeWg) {
		this.dlFeeWg = dlFeeWg;
	}


}