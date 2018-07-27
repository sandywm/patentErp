package com.patent.module;

/**
 * ActRoleInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ActRoleInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyRoleInfoTb cpyRoleInfoTb;
	private ModActInfoTb modActInfoTb;

	// Constructors

	/** default constructor */
	public ActRoleInfoTb() {
	}

	/** full constructor */
	public ActRoleInfoTb(Integer id,CpyRoleInfoTb cpyRoleInfoTb, ModActInfoTb modActInfoTb) {
		this.id = id;
		this.cpyRoleInfoTb = cpyRoleInfoTb;
		this.modActInfoTb = modActInfoTb;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CpyRoleInfoTb getCpyRoleInfoTb() {
		return this.cpyRoleInfoTb;
	}

	public void setCpyRoleInfoTb(CpyRoleInfoTb cpyRoleInfoTb) {
		this.cpyRoleInfoTb = cpyRoleInfoTb;
	}

	public ModActInfoTb getModActInfoTb() {
		return this.modActInfoTb;
	}

	public void setModActInfoTb(ModActInfoTb modActInfoTb) {
		this.modActInfoTb = modActInfoTb;
	}

}