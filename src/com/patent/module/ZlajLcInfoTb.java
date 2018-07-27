package com.patent.module;

import java.util.Date;

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
	private CpyUserInfo cpyUserInfo;
	private ZlajMainInfoTb zlajMainInfoTb;
	private String lcMz;
	private String lcDetail;
	private Double lcMzNo;
	private Date lcSDate;
	private Date lcCpyDate;
	private Date lcEDate;
	private Date lcGfDate;
	private String lcUpInfo;
	private Integer lcComStatus;
	private Integer lcPj;
	private String lcRemark;

	// Constructors

	/** default constructor */
	public ZlajLcInfoTb() {
	}

	/** minimal constructor */
	public ZlajLcInfoTb(CpyUserInfo cpyUserInfo, ZlajMainInfoTb zlajMainInfoTb,
			String lcMz, String lcDetail, Double lcMzNo, Date lcSDate,
			Date lcCpyDate, Date lcEDate, Date lcGfDate, String lcUpInfo,
			Integer lcComStatus, Integer lcPj, String lcRemark) {
		this.cpyUserInfo = cpyUserInfo;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.lcMz = lcMz;
		this.lcDetail = lcDetail;
		this.lcMzNo = lcMzNo;
		this.lcSDate = lcSDate;
		this.lcCpyDate = lcCpyDate;
		this.lcEDate = lcEDate;
		this.lcGfDate = lcGfDate;
		this.lcUpInfo = lcUpInfo;
		this.lcComStatus = lcComStatus;
		this.lcPj = lcPj;
		this.lcRemark = lcRemark;
	}

	/** full constructor */
	public ZlajLcInfoTb(Integer id,CpyUserInfo cpyUserInfo, ZlajMainInfoTb zlajMainInfoTb,
			String lcMz, String lcDetail, Double lcMzNo, Date lcSDate,
			Date lcCpyDate, Date lcEDate, Date lcGfDate, String lcUpInfo,
			Integer lcComStatus, Integer lcPj, String lcRemark) {
		this.id = id;
		this.cpyUserInfo = cpyUserInfo;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.lcMz = lcMz;
		this.lcDetail = lcDetail;
		this.lcMzNo = lcMzNo;
		this.lcSDate = lcSDate;
		this.lcCpyDate = lcCpyDate;
		this.lcEDate = lcEDate;
		this.lcGfDate = lcGfDate;
		this.lcUpInfo = lcUpInfo;
		this.lcComStatus = lcComStatus;
		this.lcPj = lcPj;
		this.lcRemark = lcRemark;
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

	public Double getLcMzNo() {
		return this.lcMzNo;
	}

	public void setLcMzNo(Double lcMzNo) {
		this.lcMzNo = lcMzNo;
	}

	public Date getLcSDate() {
		return this.lcSDate;
	}

	public void setLcSDate(Date lcSDate) {
		this.lcSDate = lcSDate;
	}

	public Date getLcCpyDate() {
		return this.lcCpyDate;
	}

	public void setLcCpyDate(Date lcCpyDate) {
		this.lcCpyDate = lcCpyDate;
	}

	public Date getLcEDate() {
		return this.lcEDate;
	}

	public void setLcEDate(Date lcEDate) {
		this.lcEDate = lcEDate;
	}

	public Date getLcGfDate() {
		return this.lcGfDate;
	}

	public void setLcGfDate(Date lcGfDate) {
		this.lcGfDate = lcGfDate;
	}

	public String getLcUpInfo() {
		return this.lcUpInfo;
	}

	public void setLcUpInfo(String lcUpInfo) {
		this.lcUpInfo = lcUpInfo;
	}

	public Integer getLcComStatus() {
		return this.lcComStatus;
	}

	public void setLcComStatus(Integer lcComStatus) {
		this.lcComStatus = lcComStatus;
	}

	public Integer getLcPj() {
		return this.lcPj;
	}

	public void setLcPj(Integer lcPj) {
		this.lcPj = lcPj;
	}

	public String getLcRemark() {
		return this.lcRemark;
	}

	public void setLcRemark(String lcRemark) {
		this.lcRemark = lcRemark;
	}

}