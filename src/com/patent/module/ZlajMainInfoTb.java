package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ZlajMainInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajMainInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private String ajNo;
	private String ajNoQt;
	private String ajNoGf;
	private String ajTitle;
	private String ajType;
	private String ajFieldId;
	private String ajSqrId;
	private String ajFmrId;
	private String ajLxrId;
	private String ajSqAddress;
	private String ajYxqId;
	private String ajUpload;
	private String ajRemark;
	private String ajEwyqId;
	private String ajApplyDate;
	private String ajStatus;
	private Integer ajFaId;
	private Integer ajStopStatus;
	private String ajStopDate;
	private String ajStopUser;
	private String ajStopUserType;
	private String ajAddDate;
	private Set<ZlajLcInfoTb> zlajLcInfoTbs = new HashSet<ZlajLcInfoTb>();
	private Set<ZlajFjInfoTb> zlajFjInfoTbs = new HashSet<ZlajFjInfoTb>();
	private Set<ZlajTzsInfoTb> zlajTzsInfoTbs = new HashSet<ZlajTzsInfoTb>();

	// Constructors

	/** default constructor */
	public ZlajMainInfoTb() {
	}

	/** minimal constructor */
	public ZlajMainInfoTb(CpyInfoTb cpyInfoTb, String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrId,
			String ajFmrId, String ajLxrId, String ajSqAddress, String ajYxqId,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus, Integer ajFaId,Integer ajStopStatus,String ajStopDate,
			String ajStopUserType,String ajStopUser,String ajAddDate) {
		this.cpyInfoTb = cpyInfoTb;
		this.ajNo = ajNo;
		this.ajNoQt = ajNoQt;
		this.ajNoGf = ajNoGf;
		this.ajTitle = ajTitle;
		this.ajType = ajType;
		this.ajFieldId = ajFieldId;
		this.ajSqrId = ajSqrId;
		this.ajFmrId = ajFmrId;
		this.ajLxrId = ajLxrId;
		this.ajSqAddress = ajSqAddress;
		this.ajYxqId = ajYxqId;
		this.ajUpload = ajUpload;
		this.ajRemark = ajRemark;
		this.ajEwyqId = ajEwyqId;
		this.ajApplyDate = ajApplyDate;
		this.ajStatus = ajStatus;
		this.ajFaId = ajFaId;
		this.ajStopStatus = ajStopStatus;
		this.ajStopDate = ajStopDate;
		this.ajStopUser = ajStopUser;
		this.ajStopUserType = ajStopUserType;
		this.ajAddDate = ajAddDate;
	}

	/** full constructor */
	public ZlajMainInfoTb(Integer id,CpyInfoTb cpyInfoTb, String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrId,
			String ajFmrId, String ajLxrId, String ajSqAddress, String ajYxqId,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus, Integer ajFaId,Integer ajStopStatus,String ajStopDate,
			String ajStopUserType,String ajStopUser,String ajAddDate) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.ajNo = ajNo;
		this.ajNoQt = ajNoQt;
		this.ajNoGf = ajNoGf;
		this.ajTitle = ajTitle;
		this.ajType = ajType;
		this.ajFieldId = ajFieldId;
		this.ajSqrId = ajSqrId;
		this.ajFmrId = ajFmrId;
		this.ajLxrId = ajLxrId;
		this.ajSqAddress = ajSqAddress;
		this.ajYxqId = ajYxqId;
		this.ajUpload = ajUpload;
		this.ajRemark = ajRemark;
		this.ajEwyqId = ajEwyqId;
		this.ajApplyDate = ajApplyDate;
		this.ajStatus = ajStatus;
		this.ajFaId = ajFaId;
		this.ajStopStatus = ajStopStatus;
		this.ajStopDate = ajStopDate;
		this.ajStopUser = ajStopUser;
		this.ajStopUserType = ajStopUserType;
		this.ajAddDate = ajAddDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CpyInfoTb getCpyInfoTb() {
		return this.cpyInfoTb;
	}

	public void setCpyInfoTb(CpyInfoTb cpyInfoTb) {
		this.cpyInfoTb = cpyInfoTb;
	}

	public String getAjNo() {
		return this.ajNo;
	}

	public void setAjNo(String ajNo) {
		this.ajNo = ajNo;
	}

	public String getAjNoGf() {
		return this.ajNoGf;
	}

	public void setAjNoGf(String ajNoGf) {
		this.ajNoGf = ajNoGf;
	}

	public String getAjTitle() {
		return this.ajTitle;
	}

	public void setAjTitle(String ajTitle) {
		this.ajTitle = ajTitle;
	}

	public String getAjType() {
		return this.ajType;
	}

	public void setAjType(String ajType) {
		this.ajType = ajType;
	}

	public String getAjFieldId() {
		return this.ajFieldId;
	}

	public void setAjFieldId(String ajFieldId) {
		this.ajFieldId = ajFieldId;
	}

	public String getAjSqrId() {
		return this.ajSqrId;
	}

	public void setAjSqrId(String ajSqrId) {
		this.ajSqrId = ajSqrId;
	}

	public String getAjFmrId() {
		return this.ajFmrId;
	}

	public void setAjFmrId(String ajFmrId) {
		this.ajFmrId = ajFmrId;
	}

	public String getAjLxrId() {
		return this.ajLxrId;
	}

	public void setAjLxrId(String ajLxrId) {
		this.ajLxrId = ajLxrId;
	}

	public String getAjSqAddress() {
		return this.ajSqAddress;
	}

	public void setAjSqAddress(String ajSqAddress) {
		this.ajSqAddress = ajSqAddress;
	}

	public String getAjYxqId() {
		return this.ajYxqId;
	}

	public void setAjYxqId(String ajYxqId) {
		this.ajYxqId = ajYxqId;
	}

	public String getAjUpload() {
		return this.ajUpload;
	}

	public void setAjUpload(String ajUpload) {
		this.ajUpload = ajUpload;
	}

	public String getAjRemark() {
		return this.ajRemark;
	}

	public void setAjRemark(String ajRemark) {
		this.ajRemark = ajRemark;
	}

	public String getAjEwyqId() {
		return this.ajEwyqId;
	}

	public void setAjEwyqId(String ajEwyqId) {
		this.ajEwyqId = ajEwyqId;
	}

	public String getAjApplyDate() {
		return this.ajApplyDate;
	}

	public void setAjApplyDate(String ajApplyDate) {
		this.ajApplyDate = ajApplyDate;
	}

	public String getAjStatus() {
		return this.ajStatus;
	}

	public void setAjStatus(String ajStatus) {
		this.ajStatus = ajStatus;
	}

	public Integer getAjFaId() {
		return this.ajFaId;
	}

	public void setAjFaId(Integer ajFaId) {
		this.ajFaId = ajFaId;
	}

	public Set<ZlajLcInfoTb> getZlajLcInfoTbs() {
		return zlajLcInfoTbs;
	}

	public void setZlajLcInfoTbs(Set<ZlajLcInfoTb> zlajLcInfoTbs) {
		this.zlajLcInfoTbs = zlajLcInfoTbs;
	}

	public Set<ZlajFjInfoTb> getZlajFjInfoTbs() {
		return zlajFjInfoTbs;
	}

	public void setZlajFjInfoTbs(Set<ZlajFjInfoTb> zlajFjInfoTbs) {
		this.zlajFjInfoTbs = zlajFjInfoTbs;
	}

	public Set<ZlajTzsInfoTb> getZlajTzsInfoTbs() {
		return zlajTzsInfoTbs;
	}

	public void setZlajTzsInfoTbs(Set<ZlajTzsInfoTb> zlajTzsInfoTbs) {
		this.zlajTzsInfoTbs = zlajTzsInfoTbs;
	}

	public Integer getAjStopStatus() {
		return ajStopStatus;
	}

	public void setAjStopStatus(Integer ajStopStatus) {
		this.ajStopStatus = ajStopStatus;
	}

	public String getAjAddDate() {
		return ajAddDate;
	}

	public void setAjAddDate(String ajAddDate) {
		this.ajAddDate = ajAddDate;
	}

	public String getAjNoQt() {
		return ajNoQt;
	}

	public void setAjNoQt(String ajNoQt) {
		this.ajNoQt = ajNoQt;
	}

	public String getAjStopDate() {
		return ajStopDate;
	}

	public void setAjStopDate(String ajStopDate) {
		this.ajStopDate = ajStopDate;
	}

	public String getAjStopUser() {
		return ajStopUser;
	}

	public void setAjStopUser(String ajStopUser) {
		this.ajStopUser = ajStopUser;
	}

	public String getAjStopUserType() {
		return ajStopUserType;
	}

	public void setAjStopUserType(String ajStopUserType) {
		this.ajStopUserType = ajStopUserType;
	}


}