package com.patent.module;

import java.sql.Timestamp;

/**
 * SendEmailInfo entity. @author MyEclipse Persistence Tools
 */

public class SendEmailCodeInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userEmail;
	private String code;
	private Timestamp sendTime;
	private Integer useStatus;

	// Constructors

	/** default constructor */
	public SendEmailCodeInfo() {
	}

	/** full constructor */
	public SendEmailCodeInfo(String userEmail, String code, Timestamp sendTime,
			Integer useStatus) {
		this.userEmail = userEmail;
		this.code = code;
		this.sendTime = sendTime;
		this.useStatus = useStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getUseStatus() {
		return this.useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

}