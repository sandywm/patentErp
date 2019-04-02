package com.patent.module;

/**
 * MailInfoTb entity. @author MyEclipse Persistence Tools
 */

public class MailInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mailType;
	private String sendInfo;
	private Integer acceptUserId;
	private String userType;
	private String mailTitle;
	private String mailContent;
	private String sendTime;
	private Integer readStatus;
	private Integer zlId;

	// Constructors

	/** default constructor */
	public MailInfoTb() {
	}

	public MailInfoTb(String mailType, String sendInfo, Integer acceptUserId,
			String userType, String mailTitle, String mailContent,
			String sendTime, Integer readStatus,Integer zlId) {
		this.mailType = mailType;
		this.sendInfo = sendInfo;
		this.acceptUserId = acceptUserId;
		this.userType = userType;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		this.sendTime = sendTime;
		this.readStatus = readStatus;
		this.zlId = zlId;
	}
	
	/** full constructor */
	public MailInfoTb(Integer id,String mailType, String sendInfo, Integer acceptUserId,
			String userType, String mailTitle, String mailContent,
			String sendTime, Integer readStatus,Integer zlId) {
		this.id = id;
		this.mailType = mailType;
		this.sendInfo = sendInfo;
		this.acceptUserId = acceptUserId;
		this.userType = userType;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		this.sendTime = sendTime;
		this.readStatus = readStatus;
		this.zlId = zlId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMailType() {
		return this.mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getSendInfo() {
		return this.sendInfo;
	}

	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}

	public Integer getAcceptUserId() {
		return this.acceptUserId;
	}

	public void setAcceptUserId(Integer acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMailTitle() {
		return this.mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return this.mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getReadStatus() {
		return this.readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public Integer getZlId() {
		return zlId;
	}

	public void setZlId(Integer zlId) {
		this.zlId = zlId;
	}

}