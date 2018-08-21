package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ModuleInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ModuleInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String modName;
	private String modPic;
	private String resUrl;
	private Integer orderNo;
	private Integer showStatus;
	private Integer modLevel;
	private Set<ModActInfoTb> modActInfoTbs = new HashSet<ModActInfoTb>();

	// Constructors

	/** default constructor */
	public ModuleInfoTb() {
	}

	/** minimal constructor */
	public ModuleInfoTb(String modName, String modPic, String resUrl,
			Integer orderNo, Integer showStatus,Integer modLevel) {
		this.modName = modName;
		this.modPic = modPic;
		this.resUrl = resUrl;
		this.orderNo = orderNo;
		this.modLevel = modLevel;
		this.showStatus = showStatus;
	}

	/** full constructor */
	public ModuleInfoTb(Integer id,String modName, String modPic, String resUrl,
			Integer orderNo, Integer showStatus,Integer modLevel) {
		this.id = id;
		this.modName = modName;
		this.modPic = modPic;
		this.resUrl = resUrl;
		this.orderNo = orderNo;
		this.modLevel = modLevel;
		this.showStatus = showStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModName() {
		return this.modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getModPic() {
		return this.modPic;
	}

	public void setModPic(String modPic) {
		this.modPic = modPic;
	}

	public String getResUrl() {
		return this.resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getModLevel() {
		return this.modLevel;
	}

	public void setModLevel(Integer modLevel) {
		this.modLevel = modLevel;
	}

	public Set<ModActInfoTb> getModActInfoTbs() {
		return this.modActInfoTbs;
	}

	public void setModActInfoTbs(Set<ModActInfoTb> modActInfoTbs) {
		this.modActInfoTbs = modActInfoTbs;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

}