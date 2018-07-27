package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * FeeTypeInfoTb entity. @author MyEclipse Persistence Tools
 */

public class FeeTypeInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String feeName;
	private String feeStatus;
	private Set<ZlajFeeInfoTb> zlajFeeInfoTbs = new HashSet<ZlajFeeInfoTb>();

	// Constructors

	/** default constructor */
	public FeeTypeInfoTb() {
	}

	/** minimal constructor */
	public FeeTypeInfoTb(String feeName, String feeStatus) {
		this.feeName = feeName;
		this.feeStatus = feeStatus;
	}

	/** full constructor */
	public FeeTypeInfoTb(Integer id,String feeName, String feeStatus) {
		this.id = id;
		this.feeName = feeName;
		this.feeStatus = feeStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getFeeStatus() {
		return this.feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	public Set<ZlajFeeInfoTb> getZlajFeeInfoTbs() {
		return this.zlajFeeInfoTbs;
	}

	public void setZlajFeeInfoTbs(Set<ZlajFeeInfoTb> zlajFeeInfoTbs) {
		this.zlajFeeInfoTbs = zlajFeeInfoTbs;
	}

}