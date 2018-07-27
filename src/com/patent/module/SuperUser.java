package com.patent.module;

/**
 * SuperUser entity. @author MyEclipse Persistence Tools
 */

public class SuperUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String account;
	private String password;
	private String userName;
	private Integer yxStatus;
	private String userType;
	private Integer loginTimes;

	// Constructors

	/** default constructor */
	public SuperUser() {
	}

	/** full constructor */
	public SuperUser(String account, String password, String userName,
			Integer yxStatus,String userType,Integer loginTimes) {
		this.account = account;
		this.password = password;
		this.userName = userName;
		this.yxStatus = yxStatus;
		this.userType = userType;
		this.loginTimes = loginTimes;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getYxStatus() {
		return this.yxStatus;
	}

	public void setYxStatus(Integer yxStatus) {
		this.yxStatus = yxStatus;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

}