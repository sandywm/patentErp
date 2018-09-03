package com.patent.module;


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
	private String tzsName;
	private String tzsFwr;
	private String tzsGfr;

	// Constructors

	/** default constructor */
	public ZlajTzsInfoTb() {
	}

	/** minimal constructor */
	public ZlajTzsInfoTb(ZlajMainInfoTb zlajMainInfoTb, String tzsName,
			String tzsFwr, String tzsGfr) {
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.tzsName = tzsName;
		this.tzsFwr = tzsFwr;
		this.tzsGfr = tzsGfr;
	}

	/** full constructor */
	public ZlajTzsInfoTb(Integer id,ZlajMainInfoTb zlajMainInfoTb, String tzsName,
			String tzsFwr, String tzsGfr) {
		this.id = id;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.tzsName = tzsName;
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

	public String getTzsName() {
		return this.tzsName;
	}

	public void setTzsName(String tzsName) {
		this.tzsName = tzsName;
	}

	public String getTzsFwr() {
		return this.tzsFwr;
	}

	public void setTzsFwr(String tzsFwr) {
		this.tzsFwr = tzsFwr;
	}

	public String getTzsGfr() {
		return this.tzsGfr;
	}

	public void setTzsGfr(String tzsGfr) {
		this.tzsGfr = tzsGfr;
	}

}