package com.patent.module;

/**
 * CpyRoleUserInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CpyRoleUserInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyUserInfo cpyUserInfo;
	private CpyRoleInfoTb cpyRoleInfoTb;

	// Constructors

	/** default constructor */
	public CpyRoleUserInfoTb() {
	}

	/** full constructor */
	public CpyRoleUserInfoTb(CpyUserInfo cpyUserInfo,
			CpyRoleInfoTb cpyRoleInfoTb) {
		this.cpyUserInfo = cpyUserInfo;
		this.cpyRoleInfoTb = cpyRoleInfoTb;
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

	public CpyRoleInfoTb getCpyRoleInfoTb() {
		return this.cpyRoleInfoTb;
	}

	public void setCpyRoleInfoTb(CpyRoleInfoTb cpyRoleInfoTb) {
		this.cpyRoleInfoTb = cpyRoleInfoTb;
	}

}