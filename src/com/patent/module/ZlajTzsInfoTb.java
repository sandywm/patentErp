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
	private Integer ajId;
	private String ajNo;
	private String tzsName;
	private String tzsFwr;
	private String fwSerial;
	private String tzsGfr;
	private String tzsPath;
	private CpyUserInfo user;
	private String uploadTime;
	private Integer readStatus;
	private String readDetail;
	private CpyInfoTb cpy;
	private String tzsType;

	// Constructors

	/** default constructor */
	public ZlajTzsInfoTb() {
	}

	/** minimal constructor */
	public ZlajTzsInfoTb(Integer ajId, String ajNo,String tzsName,
			String tzsFwr, String tzsGfr,String fwSerial,String tzsPath,CpyUserInfo user,
			String uploadTime,Integer readStatus,String readDetail,CpyInfoTb cpy,String tzsType) {
		this.ajId = ajId;
		this.tzsName = tzsName;
		this.ajNo = ajNo;
		this.tzsFwr = tzsFwr;
		this.tzsGfr = tzsGfr;
		this.fwSerial = fwSerial;
		this.tzsPath = tzsPath;
		this.user = user;
		this.uploadTime = uploadTime;
		this.readStatus = readStatus;
		this.readDetail = readDetail;
		this.cpy = cpy;
		this.tzsType = tzsType;
	}

	/** full constructor */
	public ZlajTzsInfoTb(Integer id,Integer ajId, String ajNo,String tzsName,
			String tzsFwr, String tzsGfr,String fwSerial,String tzsPath,CpyUserInfo user,
			String uploadTime,Integer readStatus,String readDetail,CpyInfoTb cpy,String tzsType) {
		this.id = id;
		this.ajId = ajId;
		this.tzsName = tzsName;
		this.ajNo = ajNo;
		this.tzsFwr = tzsFwr;
		this.tzsGfr = tzsGfr;
		this.fwSerial = fwSerial;
		this.tzsPath = tzsPath;
		this.user = user;
		this.uploadTime = uploadTime;
		this.readStatus = readStatus;
		this.readDetail = readDetail;
		this.cpy = cpy;
		this.tzsType = tzsType;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAjId() {
		return ajId;
	}

	public void setAjId(Integer ajId) {
		this.ajId = ajId;
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

	public String getFwSerial() {
		return fwSerial;
	}

	public void setFwSerial(String fwSerial) {
		this.fwSerial = fwSerial;
	}

	public String getTzsPath() {
		return tzsPath;
	}

	public void setTzsPath(String tzsPath) {
		this.tzsPath = tzsPath;
	}

	public CpyUserInfo getUser() {
		return user;
	}

	public void setUser(CpyUserInfo user) {
		this.user = user;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public String getReadDetail() {
		return readDetail;
	}

	public void setReadDetail(String readDetail) {
		this.readDetail = readDetail;
	}

	public CpyInfoTb getCpy() {
		return cpy;
	}

	public void setCpy(CpyInfoTb cpy) {
		this.cpy = cpy;
	}

	public String getAjNo() {
		return ajNo;
	}

	public void setAjNo(String ajNo) {
		this.ajNo = ajNo;
	}

	public String getTzsType() {
		return tzsType;
	}

	public void setTzsType(String tzsType) {
		this.tzsType = tzsType;
	}

}