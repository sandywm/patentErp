package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * CpyRoleInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CpyRoleInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private String roleName;
	private String roleProfile;
	private Set<CpyRoleUserInfoTb> cpyRoleUserInfoTbs = new HashSet<CpyRoleUserInfoTb>();
	private Set<ActRoleInfoTb> actRoleInfoTbs = new HashSet<ActRoleInfoTb>();

	// Constructors

	/** default constructor */
	public CpyRoleInfoTb() {
	}

	/** minimal constructor */
	public CpyRoleInfoTb(CpyInfoTb cpyInfoTb, String roleName,String roleProfile) {
		this.cpyInfoTb = cpyInfoTb;
		this.roleName = roleName;
		this.roleProfile = roleProfile;
	}

	/** full constructor */
	public CpyRoleInfoTb(Integer id,CpyInfoTb cpyInfoTb, String roleName,
			String roleProfile) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.roleName = roleName;
		this.roleProfile = roleProfile;
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

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleProfile() {
		return this.roleProfile;
	}

	public void setRoleProfile(String roleProfile) {
		this.roleProfile = roleProfile;
	}

	public Set<CpyRoleUserInfoTb> getCpyRoleUserInfoTbs() {
		return cpyRoleUserInfoTbs;
	}

	public void setCpyRoleUserInfoTbs(Set<CpyRoleUserInfoTb> cpyRoleUserInfoTbs) {
		this.cpyRoleUserInfoTbs = cpyRoleUserInfoTbs;
	}

	public Set<ActRoleInfoTb> getActRoleInfoTbs() {
		return actRoleInfoTbs;
	}

	public void setActRoleInfoTbs(Set<ActRoleInfoTb> actRoleInfoTbs) {
		this.actRoleInfoTbs = actRoleInfoTbs;
	}


}