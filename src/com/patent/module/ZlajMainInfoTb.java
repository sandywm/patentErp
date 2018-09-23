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
	private String ajSqrName;
	private String ajFmrName;
	private String ajLxrName;
	private Double ajFjInfo;
	private String ajSqAddress;
	private String ajYxqDetail;
	private String ajUpload;
	private String ajRemark;
	private String ajEwyqId;
	private String ajApplyDate;
	private String ajStatus;
	private String ajStatusChi;
	private Integer ajFaId;
	private Integer checkUserId;
	private Integer zxUserId;
	private Integer tjUserId;
	private Integer tzsUserId;
	private Integer feeUserId;
	private Integer bzUserId;
	private Integer bzshUserId;
	private Integer bhUserId;
	private Integer pubZlId;
	private Integer ajStopStatus;
	private String ajStopDate;
	private String ajStopUser;
	private String ajStopUserType;
	private String ajAddDate;
	private Integer ajAddUserId;
	private Set<ZlajLcInfoTb> zlajLcInfoTbs = new HashSet<ZlajLcInfoTb>();
	private Set<ZlajFjInfoTb> zlajFjInfoTbs = new HashSet<ZlajFjInfoTb>();
	private Set<ZlajTzsInfoTb> zlajTzsInfoTbs = new HashSet<ZlajTzsInfoTb>();
	private Set<ZlajFeeInfoTb> zlajFeeInfoTbs = new HashSet<ZlajFeeInfoTb>();

	// Constructors

	/** default constructor */
	public ZlajMainInfoTb() {
	}

	/** minimal constructor */
	public ZlajMainInfoTb(CpyInfoTb cpyInfoTb, String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrName,
			String ajFmrName, String ajLxrName, Double ajFjInfo,String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus, String ajStatusChi,Integer ajFaId,Integer ajStopStatus,Integer pubZlId,String ajStopDate,
			String ajStopUserType,String ajStopUser,String ajAddDate,Integer checkUserId,Integer zxUserId,
			Integer tjUserId,Integer tzsUserId,Integer feeUserId,Integer bzUserId,Integer bzshUserId,Integer bhUserId,Integer ajAddUserId) {
		this.cpyInfoTb = cpyInfoTb;
		this.ajNo = ajNo;
		this.ajNoQt = ajNoQt;
		this.ajNoGf = ajNoGf;
		this.ajTitle = ajTitle;
		this.ajType = ajType;
		this.ajFieldId = ajFieldId;
		this.ajSqrName = ajSqrName;
		this.ajFmrName = ajFmrName;
		this.ajLxrName = ajLxrName;
		this.ajFjInfo = ajFjInfo;
		this.ajSqAddress = ajSqAddress;
		this.ajYxqDetail = ajYxqDetail;
		this.ajUpload = ajUpload;
		this.ajRemark = ajRemark;
		this.ajEwyqId = ajEwyqId;
		this.ajApplyDate = ajApplyDate;
		this.ajStatus = ajStatus;
		this.ajStatusChi = ajStatusChi;
		this.ajFaId = ajFaId;
		this.pubZlId = pubZlId;
		this.ajStopStatus = ajStopStatus;
		this.ajStopDate = ajStopDate;
		this.ajStopUser = ajStopUser;
		this.ajStopUserType = ajStopUserType;
		this.ajAddDate = ajAddDate;
		this.checkUserId = checkUserId;
		this.zxUserId = zxUserId;
		this.tjUserId = tjUserId;
		this.tzsUserId = tzsUserId;
		this.feeUserId = feeUserId;
		this.bzUserId = bzUserId;
		this.bzshUserId = bzshUserId;
		this.bhUserId = bhUserId;
		this.ajAddUserId = ajAddUserId;
	}

	/** full constructor */
	public ZlajMainInfoTb(Integer id,CpyInfoTb cpyInfoTb, String ajNo, String ajNoQt,String ajNoGf,
			String ajTitle, String ajType, String ajFieldId, String ajSqrName,
			String ajFmrName, String ajLxrName, Double ajFjInfo,String ajSqAddress, String ajYxqDetail,
			String ajUpload, String ajRemark, String ajEwyqId,
			String ajApplyDate, String ajStatus, String ajStatusChi,Integer ajFaId,Integer ajStopStatus,Integer pubZlId,String ajStopDate,
			String ajStopUserType,String ajStopUser,String ajAddDate,Integer checkUserId,Integer zxUserId,
			Integer tjUserId,Integer tzsUserId,Integer feeUserId,Integer bzUserId,Integer bzshUserId,Integer bhUserId,Integer ajAddUserId) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.ajNo = ajNo;
		this.ajNoQt = ajNoQt;
		this.ajNoGf = ajNoGf;
		this.ajTitle = ajTitle;
		this.ajType = ajType;
		this.ajFieldId = ajFieldId;
		this.ajSqrName = ajSqrName;
		this.ajFmrName = ajFmrName;
		this.ajLxrName = ajLxrName;
		this.ajFjInfo = ajFjInfo;
		this.ajSqAddress = ajSqAddress;
		this.ajYxqDetail = ajYxqDetail;
		this.ajUpload = ajUpload;
		this.ajRemark = ajRemark;
		this.ajEwyqId = ajEwyqId;
		this.ajApplyDate = ajApplyDate;
		this.ajStatus = ajStatus;
		this.ajStatusChi = ajStatusChi;
		this.ajFaId = ajFaId;
		this.pubZlId = pubZlId;
		this.ajStopStatus = ajStopStatus;
		this.ajStopDate = ajStopDate;
		this.ajStopUser = ajStopUser;
		this.ajStopUserType = ajStopUserType;
		this.ajAddDate = ajAddDate;
		this.checkUserId = checkUserId;
		this.zxUserId = zxUserId;
		this.tjUserId = tjUserId;
		this.tzsUserId = tzsUserId;
		this.feeUserId = feeUserId;
		this.bzUserId = bzUserId;
		this.bzshUserId = bzshUserId;
		this.bhUserId = bhUserId;
		this.ajAddUserId = ajAddUserId;
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

	public String getAjSqrName() {
		return this.ajSqrName;
	}

	public void setAjSqrName(String ajSqrName) {
		this.ajSqrName = ajSqrName;
	}

	public String getAjFmrName() {
		return this.ajFmrName;
	}

	public void setAjFmrName(String ajFmrName) {
		this.ajFmrName = ajFmrName;
	}

	public String getAjLxrName() {
		return this.ajLxrName;
	}

	public void setAjLxrName(String ajLxrName) {
		this.ajLxrName = ajLxrName;
	}

	public String getAjSqAddress() {
		return this.ajSqAddress;
	}

	public void setAjSqAddress(String ajSqAddress) {
		this.ajSqAddress = ajSqAddress;
	}

	public String getAjYxqDetail() {
		return this.ajYxqDetail;
	}

	public void setAjYxqDetail(String ajYxqDetail) {
		this.ajYxqDetail = ajYxqDetail;
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

	public Integer getPubZlId() {
		return pubZlId;
	}

	public void setPubZlId(Integer pubZlId) {
		this.pubZlId = pubZlId;
	}

	public Integer getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Integer checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Set<ZlajFeeInfoTb> getZlajFeeInfoTbs() {
		return zlajFeeInfoTbs;
	}

	public void setZlajFeeInfoTbs(Set<ZlajFeeInfoTb> zlajFeeInfoTbs) {
		this.zlajFeeInfoTbs = zlajFeeInfoTbs;
	}

	public Integer getZxUserId() {
		return zxUserId;
	}

	public void setZxUserId(Integer zxUserId) {
		this.zxUserId = zxUserId;
	}

	public Integer getTjUserId() {
		return tjUserId;
	}

	public void setTjUserId(Integer tjUserId) {
		this.tjUserId = tjUserId;
	}

	public Integer getTzsUserId() {
		return tzsUserId;
	}

	public void setTzsUserId(Integer tzsUserId) {
		this.tzsUserId = tzsUserId;
	}

	public Integer getFeeUserId() {
		return feeUserId;
	}

	public void setFeeUserId(Integer feeUserId) {
		this.feeUserId = feeUserId;
	}

	public Integer getBzUserId() {
		return bzUserId;
	}

	public void setBzUserId(Integer bzUserId) {
		this.bzUserId = bzUserId;
	}

	public Integer getBzshUserId() {
		return bzshUserId;
	}

	public void setBzshUserId(Integer bzshUserId) {
		this.bzshUserId = bzshUserId;
	}

	public Integer getBhUserId() {
		return bhUserId;
	}

	public void setBhUserId(Integer bhUserId) {
		this.bhUserId = bhUserId;
	}

	public Integer getAjAddUserId() {
		return ajAddUserId;
	}

	public void setAjAddUserId(Integer ajAddUserId) {
		this.ajAddUserId = ajAddUserId;
	}

	public String getAjStatusChi() {
		return ajStatusChi;
	}

	public void setAjStatusChi(String ajStatusChi) {
		this.ajStatusChi = ajStatusChi;
	}

	public Double getAjFjInfo() {
		return ajFjInfo;
	}

	public void setAjFjInfo(Double ajFjInfo) {
		this.ajFjInfo = ajFjInfo;
	}


}