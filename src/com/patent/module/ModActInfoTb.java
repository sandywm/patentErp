package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ModActInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ModActInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ModuleInfoTb moduleInfoTb;
	private String actNameChi;
	private String actNameEng;
	private Integer orderNo;
	private Set<ActRoleInfoTb> actRoleInfoTbs = new HashSet<ActRoleInfoTb>();

	// Constructors

	/** default constructor */
	public ModActInfoTb() {
	}

	/** minimal constructor */
	public ModActInfoTb(ModuleInfoTb moduleInfoTb, String actNameChi,
			String actNameEng, Integer orderNo) {
		this.moduleInfoTb = moduleInfoTb;
		this.actNameChi = actNameChi;
		this.actNameEng = actNameEng;
		this.orderNo = orderNo;
	}

	/** full constructor */
	public ModActInfoTb(Integer id,ModuleInfoTb moduleInfoTb, String actNameChi,
			String actNameEng, Integer orderNo) {
		this.id = id;
		this.moduleInfoTb = moduleInfoTb;
		this.actNameChi = actNameChi;
		this.actNameEng = actNameEng;
		this.orderNo = orderNo;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ModuleInfoTb getModuleInfoTb() {
		return this.moduleInfoTb;
	}

	public void setModuleInfoTb(ModuleInfoTb moduleInfoTb) {
		this.moduleInfoTb = moduleInfoTb;
	}

	public String getActNameChi() {
		return this.actNameChi;
	}

	public void setActNameChi(String actNameChi) {
		this.actNameChi = actNameChi;
	}

	public String getActNameEng() {
		return this.actNameEng;
	}

	public void setActNameEng(String actNameEng) {
		this.actNameEng = actNameEng;
	}

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Set<ActRoleInfoTb> getActRoleInfoTbs() {
		return this.actRoleInfoTbs;
	}

	public void setActRoleInfoTbs(Set<ActRoleInfoTb> actRoleInfoTbs) {
		this.actRoleInfoTbs = actRoleInfoTbs;
	}

}