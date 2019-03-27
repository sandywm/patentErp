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
	private Double lcNo;
	private String lcTzsPath;
	private Integer createStatus;
	private Integer qrhId;
	private Set<ZlajLcMxInfoTb> zlajLcMxInfoTbs = new HashSet<ZlajLcMxInfoTb>();

	// Constructors

	/** default constructor */
	public ZlajLcInfoTb() {
	}

	/** minimal constructor */
	public ZlajLcInfoTb(ZlajMainInfoTb zlajMainInfoTb,String lcMz, String lcDetail, String lcSDate,
			String lcCpyDate, String lcEDate, String lcGfDate,Double lcNo,String lcTzsPath,Integer createStatus,Integer qrhId) {
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.lcMz = lcMz;
		this.lcDetail = lcDetail;
		this.lcSDate = lcSDate;
		this.lcCpyDate = lcCpyDate;
		this.lcEDate = lcEDate;
		this.lcGfDate = lcGfDate;
		this.lcNo = lcNo;
		this.lcTzsPath = lcTzsPath;
		this.createStatus = createStatus;
		this.qrhId = qrhId;
	}

	/** full constructor */
	public ZlajLcInfoTb(Integer id,ZlajMainInfoTb zlajMainInfoTb,String lcMz, String lcDetail, String lcSDate,
			String lcCpyDate, String lcEDate, String lcGfDate,Double lcNo,String lcTzsPath,Integer createStatus,Integer qrhId) {
		this.id = id;
		this.zlajMainInfoTb = zlajMainInfoTb;
		this.lcMz = lcMz;
		this.lcDetail = lcDetail;
		this.lcSDate = lcSDate;
		this.lcCpyDate = lcCpyDate;
		this.lcEDate = lcEDate;
		this.lcGfDate = lcGfDate;
		this.lcNo = lcNo;
		this.lcTzsPath = lcTzsPath;
		this.createStatus = createStatus;
		this.qrhId = qrhId;
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

	public Double getLcNo() {
		return lcNo;
	}

	public void setLcNo(Double lcNo) {
		this.lcNo = lcNo;
	}

	public String getLcTzsPath() {
		return lcTzsPath;
	}

	public void setLcTzsPath(String lcTzsPath) {
		this.lcTzsPath = lcTzsPath;
	}

	public Integer getCreateStatus() {
		return createStatus;
	}

	public void setCreateStatus(Integer createStatus) {
		this.createStatus = createStatus;
	}

	public Integer getQrhId() {
		return qrhId;
	}

	public void setQrhId(Integer qrhId) {
		this.qrhId = qrhId;
	}
	
}