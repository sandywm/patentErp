package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * CpyUserInfo entity. @author MyEclipse Persistence Tools
 */

public class CpyUserInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private String userName;
	private String userNamePy;
	private String userAccount;
	private String userPassword;
	private String userSex;
	private String userEmail;
	private String userTel;
	private String userInDate;
	private String userOutDate;
	private Integer userLzStatus;
	private Integer userYxStatus;
	private Integer userZxNum;
	private String userScFiledId;
	private String userScFiledName;
	private String lastLoginDate;
	private Integer userLoginTimes;
	private Integer userExper;
	private Set<CpyRoleUserInfoTb> cpyRoleUserInfoTbs = new HashSet<CpyRoleUserInfoTb>();
	private Set<ZlajFjInfoTb> zlajFjInfoTbs = new HashSet<ZlajFjInfoTb>();
	private Set<ZlajFeeInfoTb> zlajFeeInfoTbs = new HashSet<ZlajFeeInfoTb>();
	private Set<ZlajLcYjInfoTb> lcyjs = new HashSet<ZlajLcYjInfoTb>();

	// Constructors

	/** default constructor */
	public CpyUserInfo() {
	}

	/** minimal constructor */
	public CpyUserInfo(CpyInfoTb cpyInfoTb, String userName,String userNamePy,
			String userAccount, String userPassword, String userSex,
			String userEmail, String userTel, String userInDate,
			String userOutDate, Integer userLzStatus, Integer userYxStatus,
			Integer userZxNum, String userScFiledId, String userScFiledName,
			String lastLoginDate, Integer userExper,Integer userLoginTimes) {
		this.cpyInfoTb = cpyInfoTb;
		this.userName = userName;
		this.userNamePy = userNamePy;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userSex = userSex;
		this.userEmail = userEmail;
		this.userTel = userTel;
		this.userInDate = userInDate;
		this.userOutDate = userOutDate;
		this.userLzStatus = userLzStatus;
		this.userYxStatus = userYxStatus;
		this.userZxNum = userZxNum;
		this.userScFiledId = userScFiledId;
		this.userScFiledName = userScFiledName;
		this.lastLoginDate = lastLoginDate;
		this.userExper = userExper;
		this.userLoginTimes = userLoginTimes;
	}

	/** full constructor */
	public CpyUserInfo(Integer id, CpyInfoTb cpyInfoTb, String userName,String userNamePy,
			String userAccount, String userPassword, String userSex,
			String userEmail, String userTel, String userInDate,
			String userOutDate, Integer userLzStatus, Integer userYxStatus,
			Integer userZxNum, String userScFiledId, String userScFiledName,
			String lastLoginDate, Integer userExper,Integer userLoginTimes) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.userName = userName;
		this.userNamePy = userNamePy;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userSex = userSex;
		this.userEmail = userEmail;
		this.userTel = userTel;
		this.userInDate = userInDate;
		this.userOutDate = userOutDate;
		this.userLzStatus = userLzStatus;
		this.userYxStatus = userYxStatus;
		this.userZxNum = userZxNum;
		this.userScFiledId = userScFiledId;
		this.userScFiledName = userScFiledName;
		this.lastLoginDate = lastLoginDate;
		this.userExper = userExper;
		this.userLoginTimes = userLoginTimes;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserInDate() {
		return this.userInDate;
	}

	public void setUserInDate(String userInDate) {
		this.userInDate = userInDate;
	}

	public String getUserOutDate() {
		return this.userOutDate;
	}

	public void setUserOutDate(String userOutDate) {
		this.userOutDate = userOutDate;
	}

	public Integer getUserLzStatus() {
		return this.userLzStatus;
	}

	public void setUserLzStatus(Integer userLzStatus) {
		this.userLzStatus = userLzStatus;
	}

	public Integer getUserYxStatus() {
		return this.userYxStatus;
	}

	public void setUserYxStatus(Integer userYxStatus) {
		this.userYxStatus = userYxStatus;
	}

	public Integer getUserZxNum() {
		return this.userZxNum;
	}

	public void setUserZxNum(Integer userZxNum) {
		this.userZxNum = userZxNum;
	}

	public String getUserScFiledId() {
		return this.userScFiledId;
	}

	public void setUserScFiledId(String userScFiledId) {
		this.userScFiledId = userScFiledId;
	}

	public String getUserScFiledName() {
		return this.userScFiledName;
	}

	public void setUserScFiledName(String userScFiledName) {
		this.userScFiledName = userScFiledName;
	}

	public String getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getUserExper() {
		return this.userExper;
	}

	public void setUserExper(Integer userExper) {
		this.userExper = userExper;
	}

	public Set<CpyRoleUserInfoTb> getCpyRoleUserInfoTbs() {
		return cpyRoleUserInfoTbs;
	}

	public void setCpyRoleUserInfoTbs(Set<CpyRoleUserInfoTb> cpyRoleUserInfoTbs) {
		this.cpyRoleUserInfoTbs = cpyRoleUserInfoTbs;
	}

	public Set<ZlajFjInfoTb> getZlajFjInfoTbs() {
		return zlajFjInfoTbs;
	}

	public void setZlajFjInfoTbs(Set<ZlajFjInfoTb> zlajFjInfoTbs) {
		this.zlajFjInfoTbs = zlajFjInfoTbs;
	}

	public Set<ZlajFeeInfoTb> getZlajFeeInfoTbs() {
		return zlajFeeInfoTbs;
	}

	public void setZlajFeeInfoTbs(Set<ZlajFeeInfoTb> zlajFeeInfoTbs) {
		this.zlajFeeInfoTbs = zlajFeeInfoTbs;
	}

	public String getUserNamePy() {
		return userNamePy;
	}

	public void setUserNamePy(String userNamePy) {
		this.userNamePy = userNamePy;
	}

	public Integer getUserLoginTimes() {
		return userLoginTimes;
	}

	public void setUserLoginTimes(Integer userLoginTimes) {
		this.userLoginTimes = userLoginTimes;
	}

	public Set<ZlajLcYjInfoTb> getLcyjs() {
		return lcyjs;
	}

	public void setLcyjs(Set<ZlajLcYjInfoTb> lcyjs) {
		this.lcyjs = lcyjs;
	}

}