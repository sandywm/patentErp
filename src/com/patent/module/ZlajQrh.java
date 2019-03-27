package com.patent.module;

/**
 * ZlajQrh entity. @author MyEclipse Persistence Tools
 */

public class ZlajQrh implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String zipName;
	private String zipPath;
	private Integer createUserId;
	private String createUserName;
	private String createTime;
	private Integer cpyId;

	// Constructors

	/** default constructor */
	public ZlajQrh() {
	}

	/** full constructor */
	public ZlajQrh(String zipName, String zipPath, Integer createUserId,
			String createUserName, String createTime,Integer cpyId) {
		this.zipName = zipName;
		this.zipPath = zipPath;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.cpyId = cpyId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZipName() {
		return this.zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public String getZipPath() {
		return this.zipPath;
	}

	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}

	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCpyId() {
		return cpyId;
	}

	public void setCpyId(Integer cpyId) {
		this.cpyId = cpyId;
	}

}