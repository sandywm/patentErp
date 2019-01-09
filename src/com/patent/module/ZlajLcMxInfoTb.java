package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * ZlajLcMxInfoTb entity. @author MyEclipse Persistence Tools
 */

public class ZlajLcMxInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer lcFzUserId;
	private ZlajLcInfoTb zlajLcInfoTb;
	private String lcMxName;
	private Double lcMxNo;
	private String lcMxSDate;
	private String lcMxEDate;
	private String lcMxUpFile;
	private Integer lcMxUpUserId;
	private String lcMxUpDate;
	private String lcMxUpSize;
	private Double lcMxFee;
	private Integer lcPjScore;
	private String lcMxRemark;
	private Integer yjCheckStatus;
	private String lastUpFileBz;
	private String lastUpFileBzSc;
	private Integer lastUpUserIdBz;
	private Integer lastUpUserIdBzSc;
	private String lastBzScRemark;
	private Set<ZlajLcYjInfoTb> lcyjs = new HashSet<ZlajLcYjInfoTb>();

	// Constructors

	/** default constructor */
	public ZlajLcMxInfoTb() {
	}

	/** minimal constructor */
	public ZlajLcMxInfoTb(Integer lcFzUserId, ZlajLcInfoTb zlajLcInfoTb,
			String lcMxName, Double lcMxNo, String lcMxSDate, String lcMxEDate,
			String lcMxUpFile, Integer lcMxUpUserId, String lcMxUpDate,
			String lcMxUpSize, Double lcMxFee, String lcMxRemark,Integer lcPjScore,Integer yjCheckStatus,
			String lastUpFileBz,String lastUpFileBzSc,Integer lastUpUserIdBz,Integer lastUpUserIdBzSc,String lastBzScRemark) {
		this.lcFzUserId = lcFzUserId;
		this.zlajLcInfoTb = zlajLcInfoTb;
		this.lcMxName = lcMxName;
		this.lcMxNo = lcMxNo;
		this.lcMxSDate = lcMxSDate;
		this.lcMxEDate = lcMxEDate;
		this.lcMxUpFile = lcMxUpFile;
		this.lcMxUpUserId = lcMxUpUserId;
		this.lcMxUpDate = lcMxUpDate;
		this.lcMxUpSize = lcMxUpSize;
		this.lcMxRemark = lcMxRemark;
		this.lcMxFee =  lcMxFee;
		this.lcPjScore = lcPjScore;
		this.yjCheckStatus = yjCheckStatus;
		this.lastUpFileBzSc = lastUpFileBzSc;
		this.lastUpFileBz = lastUpFileBz;
		this.lastUpUserIdBz = lastUpUserIdBz;
		this.lastUpUserIdBzSc = lastUpUserIdBzSc;
		this.lastBzScRemark = lastBzScRemark;
	}

	/** full constructor */
	public ZlajLcMxInfoTb(Integer id,Integer lcFzUserId, ZlajLcInfoTb zlajLcInfoTb,
			String lcMxName, Double lcMxNo, String lcMxSDate, String lcMxEDate,
			String lcMxUpFile, Integer lcMxUpUserId, String lcMxUpDate,
			String lcMxUpSize, Double lcMxFee, String lcMxRemark,Integer lcPjScore,Integer yjCheckStatus,
			String lastUpFileBz,String lastUpFileBzSc,Integer lastUpUserIdBz,Integer lastUpUserIdBzSc,String lastBzScRemark) {
		this.lcFzUserId = lcFzUserId;
		this.zlajLcInfoTb = zlajLcInfoTb;
		this.lcMxName = lcMxName;
		this.lcMxNo = lcMxNo;
		this.lcMxSDate = lcMxSDate;
		this.lcMxEDate = lcMxEDate;
		this.lcMxUpFile = lcMxUpFile;
		this.lcMxUpUserId = lcMxUpUserId;
		this.lcMxUpDate = lcMxUpDate;
		this.lcMxUpSize = lcMxUpSize;
		this.lcMxRemark = lcMxRemark;
		this.lcMxFee =  lcMxFee;
		this.lcPjScore = lcPjScore;
		this.yjCheckStatus = yjCheckStatus;
		this.lastUpFileBzSc = lastUpFileBzSc;
		this.lastUpFileBz = lastUpFileBz;
		this.lastUpUserIdBz = lastUpUserIdBz;
		this.lastUpUserIdBzSc = lastUpUserIdBzSc;
		this.lastBzScRemark = lastBzScRemark;
	}

	// Property accessors

	public String getLastUpFileBz() {
		return lastUpFileBz;
	}

	public void setLastUpFileBz(String lastUpFileBz) {
		this.lastUpFileBz = lastUpFileBz;
	}

	public String getLastUpFileBzSc() {
		return lastUpFileBzSc;
	}

	public void setLastUpFileBzSc(String lastUpFileBzSc) {
		this.lastUpFileBzSc = lastUpFileBzSc;
	}

	public Integer getLastUpUserIdBz() {
		return lastUpUserIdBz;
	}

	public void setLastUpUserIdBz(Integer lastUpUserIdBz) {
		this.lastUpUserIdBz = lastUpUserIdBz;
	}

	public Integer getLastUpUserIdBzSc() {
		return lastUpUserIdBzSc;
	}

	public void setLastUpUserIdBzSc(Integer lastUpUserIdBzSc) {
		this.lastUpUserIdBzSc = lastUpUserIdBzSc;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLcFzUserId() {
		return lcFzUserId;
	}

	public void setLcFzUserId(Integer lcFzUserId) {
		this.lcFzUserId = lcFzUserId;
	}

	public ZlajLcInfoTb getZlajLcInfoTb() {
		return this.zlajLcInfoTb;
	}

	public void setZlajLcInfoTb(ZlajLcInfoTb zlajLcInfoTb) {
		this.zlajLcInfoTb = zlajLcInfoTb;
	}

	public String getLcMxName() {
		return this.lcMxName;
	}

	public void setLcMxName(String lcMxName) {
		this.lcMxName = lcMxName;
	}

	public Double getLcMxNo() {
		return this.lcMxNo;
	}

	public void setLcMxNo(Double lcMxNo) {
		this.lcMxNo = lcMxNo;
	}

	public String getLcMxSDate() {
		return this.lcMxSDate;
	}

	public void setLcMxSDate(String lcMxSDate) {
		this.lcMxSDate = lcMxSDate;
	}

	public String getLcMxEDate() {
		return this.lcMxEDate;
	}

	public void setLcMxEDate(String lcMxEDate) {
		this.lcMxEDate = lcMxEDate;
	}

	public String getLcMxUpFile() {
		return this.lcMxUpFile;
	}

	public void setLcMxUpFile(String lcMxUpFile) {
		this.lcMxUpFile = lcMxUpFile;
	}

	public Integer getLcMxUpUserId() {
		return this.lcMxUpUserId;
	}

	public void setLcMxUpUserId(Integer lcMxUpUserId) {
		this.lcMxUpUserId = lcMxUpUserId;
	}

	public String getLcMxUpDate() {
		return this.lcMxUpDate;
	}

	public void setLcMxUpDate(String lcMxUpDate) {
		this.lcMxUpDate = lcMxUpDate;
	}

	public String getLcMxUpSize() {
		return this.lcMxUpSize;
	}

	public void setLcMxUpSize(String lcMxUpSize) {
		this.lcMxUpSize = lcMxUpSize;
	}

	public String getLcMxRemark() {
		return this.lcMxRemark;
	}

	public void setLcMxRemark(String lcMxRemark) {
		this.lcMxRemark = lcMxRemark;
	}

	public Double getLcMxFee() {
		return lcMxFee;
	}

	public void setLcMxFee(Double lcMxFee) {
		this.lcMxFee = lcMxFee;
	}

	public Integer getLcPjScore() {
		return lcPjScore;
	}

	public void setLcPjScore(Integer lcPjScore) {
		this.lcPjScore = lcPjScore;
	}

	public Set<ZlajLcYjInfoTb> getLcyjs() {
		return lcyjs;
	}

	public void setLcyjs(Set<ZlajLcYjInfoTb> lcyjs) {
		this.lcyjs = lcyjs;
	}

	public Integer getYjCheckStatus() {
		return yjCheckStatus;
	}

	public void setYjCheckStatus(Integer yjCheckStatus) {
		this.yjCheckStatus = yjCheckStatus;
	}

	public String getLastBzScRemark() {
		return lastBzScRemark;
	}

	public void setLastBzScRemark(String lastBzScRemark) {
		this.lastBzScRemark = lastBzScRemark;
	}

}