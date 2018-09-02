package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ZlajLcInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajLcInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ZlajMainInfoTb zlajMainInfoTb;
	private String lcMz;
	private String lcDetail;
	private String lcSDate;
	private String lcCpyDate;
	private String lcEDate;
	private String lcGfDate;
	private Set<ZlajLcMxInfoTb> zlajLcMxInfoTbs = new HashSet<ZlajLcMxInfoTb>();

	// Constructors

	/** default constructor */
	public ZlajLcInfoTb() {
	}

	/** minimal constructor */
	public ZlajLcInfoTb(ZlajMainInfoTb zlajMainInfoTb,String lcMz, String lcDetail, String lcSDate,
			String lcCpyDate, String lcEDate, String lcGfDate) {
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.lcMz = lcMz;
		this.lcDetail = lcDetail;
		this.lcSDate = lcSDate;
		this.lcCpyDate = lcCpyDate;
		this.lcEDate = lcEDate;
		this.lcGfDate = lcGfDate;
	}

	/** full constructor */
	public ZlajLcInfoTb(Integer id,ZlajMainInfoTb zlajMainInfoTb,String lcMz, String lcDetail, String lcSDate,
			String lcCpyDate, String lcEDate, String lcGfDate) {
		this.id = id;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.lcMz = lcMz;
		this.lcDetail = lcDetail;
		this.lcSDate = lcSDate;
		this.lcCpyDate = lcCpyDate;
		this.lcEDate = lcEDate;
		this.lcGfDate = lcGfDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZlajMainInfoTb getZlajMainInfoTb() {
		return this.zlajMainInfoTb;
	}

	public void setZlajMainInfoTb(ZlajMainInfoTb zlajMainInfoTb) {
		this.zlajMainInfoTb = zlajMainInfoTb;
	}

	public String getLcMz() {
		return this.lcMz;
	}

	public void setLcMz(String lcMz) {
		this.lcMz = lcMz;
	}

	public String getLcDetail() {
		return this.lcDetail;
	}

	public void setLcDetail(String lcDetail) {
		this.lcDetail = lcDetail;
	}

	public String getLcSDate() {
		return this.lcSDate;
	}

	public void setLcSDate(String lcSDate) {
		this.lcSDate = lcSDate;
	}

	public String getLcCpyDate() {
		return this.lcCpyDate;
	}

	public void setLcCpyDate(String lcCpyDate) {
		this.lcCpyDate = lcCpyDate;
	}

	public String getLcEDate() {
		return this.lcEDate;
	}

	public void setLcEDate(String lcEDate) {
		this.lcEDate = lcEDate;
	}

	public String getLcGfDate() {
		return this.lcGfDate;
	}

	public void setLcGfDate(String lcGfDate) {
		this.lcGfDate = lcGfDate;
	}

	public Set<ZlajLcMxInfoTb> getZlajLcMxInfoTbs() {
		return zlajLcMxInfoTbs;
	}

	public void setZlajLcMxInfoTbs(Set<ZlajLcMxInfoTb> zlajLcMxInfoTbs) {
		this.zlajLcMxInfoTbs = zlajLcMxInfoTbs;
	}
	
}