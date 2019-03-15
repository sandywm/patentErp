package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ZlajQqsInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajQqsInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String zlTypeChi;
	private String zlTypeEng;
	private String zlQqs;
	private String feeTypeName;
	private Double feePrice;
	private Set<ZlajZdSubmitTb> zlajZdSubmitTbs = new HashSet<ZlajZdSubmitTb>();

	// Constructors

	/** default constructor */
	public ZlajQqsInfoTb() {
	}

	/** full constructor */
	public ZlajQqsInfoTb(String zlTypeChi, String zlTypeEng, String zlQqs,
			Double feePrice,String feeTypeName) {
		this.zlTypeChi = zlTypeChi;
		this.zlTypeEng = zlTypeEng;
		this.zlQqs = zlQqs;
		this.feePrice = feePrice;
		this.feeTypeName = feeTypeName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZlTypeChi() {
		return this.zlTypeChi;
	}

	public void setZlTypeChi(String zlTypeChi) {
		this.zlTypeChi = zlTypeChi;
	}

	public String getZlTypeEng() {
		return this.zlTypeEng;
	}

	public void setZlTypeEng(String zlTypeEng) {
		this.zlTypeEng = zlTypeEng;
	}

	public String getZlQqs() {
		return this.zlQqs;
	}

	public void setZlQqs(String zlQqs) {
		this.zlQqs = zlQqs;
	}

	public Double getFeePrice() {
		return this.feePrice;
	}

	public void setFeePrice(Double feePrice) {
		this.feePrice = feePrice;
	}

	public Set<ZlajZdSubmitTb> getZlajZdSubmitTbs() {
		return zlajZdSubmitTbs;
	}

	public void setZlajZdSubmitTbs(Set<ZlajZdSubmitTb> zlajZdSubmitTbs) {
		this.zlajZdSubmitTbs = zlajZdSubmitTbs;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}


}