package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ApplyInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ApplyInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String appType;
	private String appName;
	private String appNamePy;
	private String appICard;
	private String appAddress;
	private String appAccount;
	private String appPass;
	private String appLxr;
	private String appTel;
	private String appEmail;
	private String appQq;
	private String lastLoginDate;
	private Integer userLoginTimes; 
	private Set<PubZlInfoTb> pubZlInfoTbs = new HashSet<PubZlInfoTb>();

	// Constructors

	/** default constructor */
	public ApplyInfoTb() {
	}

	/** minimal constructor */
	public ApplyInfoTb(String appType, String appName, String appNamePy,String appAccount,
			String appPass, String appLxr, String appTel,String lastLoginDate,Integer userLoginTimes) {
		this.appType = appType;
		this.appName = appName;
		this.appNamePy = appNamePy;
		this.appAccount = appAccount;
		this.appPass = appPass;
		this.appLxr = appLxr;
		this.appTel = appTel;
		this.lastLoginDate = lastLoginDate;
		this.userLoginTimes = userLoginTimes;
	}

	/** full constructor */
	public ApplyInfoTb(Integer id,String appType, String appName, String appNamePy,String appICard,
			String appAddress, String appAccount, String appPass,
			String appLxr, String appTel, String appEmail, String appQq,String lastLoginDate,Integer userLoginTimes) {
		this.id = id;
		this.appType = appType;
		this.appName = appName;
		this.appNamePy = appNamePy;
		this.appICard = appICard;
		this.appAddress = appAddress;
		this.appAccount = appAccount;
		this.appPass = appPass;
		this.appLxr = appLxr;
		this.appTel = appTel;
		this.appEmail = appEmail;
		this.appQq = appQq;
		this.lastLoginDate = lastLoginDate;
		this.userLoginTimes = userLoginTimes;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppICard() {
		return this.appICard;
	}

	public void setAppICard(String appICard) {
		this.appICard = appICard;
	}

	public String getAppAddress() {
		return this.appAddress;
	}

	public void setAppAddress(String appAddress) {
		this.appAddress = appAddress;
	}

	public String getAppAccount() {
		return this.appAccount;
	}

	public void setAppAccount(String appAccount) {
		this.appAccount = appAccount;
	}

	public String getAppPass() {
		return this.appPass;
	}

	public void setAppPass(String appPass) {
		this.appPass = appPass;
	}

	public String getAppLxr() {
		return this.appLxr;
	}

	public void setAppLxr(String appLxr) {
		this.appLxr = appLxr;
	}

	public String getAppTel() {
		return this.appTel;
	}

	public void setAppTel(String appTel) {
		this.appTel = appTel;
	}

	public String getAppEmail() {
		return this.appEmail;
	}

	public void setAppEmail(String appEmail) {
		this.appEmail = appEmail;
	}

	public String getAppQq() {
		return this.appQq;
	}

	public void setAppQq(String appQq) {
		this.appQq = appQq;
	}

	public Set<PubZlInfoTb> getPubZlInfoTbs() {
		return this.pubZlInfoTbs;
	}

	public void setPubZlInfoTbs(Set<PubZlInfoTb> pubZlInfoTbs) {
		this.pubZlInfoTbs = pubZlInfoTbs;
	}

	public String getAppNamePy() {
		return appNamePy;
	}

	public void setAppNamePy(String appNamePy) {
		this.appNamePy = appNamePy;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getUserLoginTimes() {
		return userLoginTimes;
	}

	public void setUserLoginTimes(Integer userLoginTimes) {
		this.userLoginTimes = userLoginTimes;
	}

}