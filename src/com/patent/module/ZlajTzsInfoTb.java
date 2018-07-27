package com.patent.module;

import java.util.Date;

/**
 * ZlajTzsInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajTzsInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ZlajMainInfoTb zlajMainInfoTb;
	private String tzdName;
	private Date tzsFwr;
	private Date tzsGfr;

	// Constructors

	/** default constructor */
	public ZlajTzsInfoTb() {
	}

	/** minimal constructor */
	public ZlajTzsInfoTb(ZlajMainInfoTb zlajMainInfoTb, String tzdName,
			Date tzsFwr, Date tzsGfr) {
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.tzdName = tzdName;
		this.tzsFwr = tzsFwr;
		this.tzsGfr = tzsGfr;
	}

	/** full constructor */
	public ZlajTzsInfoTb(Integer id,ZlajMainInfoTb zlajMainInfoTb, String tzdName,
			Date tzsFwr, Date tzsGfr) {
		this.id = id;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.tzdName = tzdName;
		this.tzsFwr = tzsFwr;
		this.tzsGfr = tzsGfr;
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

	public String getTzdName() {
		return this.tzdName;
	}

	public void setTzdName(String tzdName) {
		this.tzdName = tzdName;
	}

	public Date getTzsFwr() {
		return this.tzsFwr;
	}

	public void setTzsFwr(Date tzsFwr) {
		this.tzsFwr = tzsFwr;
	}

	public Date getTzsGfr() {
		return this.tzsGfr;
	}

	public void setTzsGfr(Date tzsGfr) {
		this.tzsGfr = tzsGfr;
	}

}