package com.patent.json;

import java.util.List;

public class QrhJson {

	private String zlSerialNo;//案件序列号
	private String zlTitle;//发明名称
	private String sqlx;//申请类型
	private String ssStatusChi;//是否提出实审
	private String tqgkStatucChi;//是否提前公开
	private String fjStatucChi;//是否已做费减
	private String fmrInfo;//发明人
	private String fmrICard;//第一发明人身份证号
	private List<SqrJson> sqrList;//申请人联系人信息
	private String wts;//代理委托书
	
	public QrhJson(){}
	
	public QrhJson(String zlSerialNo,String zlTitle,String sqlx, String ssStatusChi, String tqgkStatucChi,
			String fjStatucChi, String fmrInfo, String fmrICard,
			List<SqrJson> sqrList, String wts) {
		this.zlSerialNo = zlSerialNo;
		this.zlTitle = zlTitle;
		this.sqlx = sqlx;
		this.ssStatusChi = ssStatusChi;
		this.tqgkStatucChi = tqgkStatucChi;
		this.fjStatucChi = fjStatucChi;
		this.fmrInfo = fmrInfo;
		this.fmrICard = fmrICard;
		this.sqrList = sqrList;
		this.wts = wts;
	}

	public String getSqlx() {
		return sqlx;
	}

	public void setSqlx(String sqlx) {
		this.sqlx = sqlx;
	}

	public String getSsStatusChi() {
		return ssStatusChi;
	}

	public void setSsStatusChi(String ssStatusChi) {
		this.ssStatusChi = ssStatusChi;
	}

	public String getTqgkStatucChi() {
		return tqgkStatucChi;
	}

	public void setTqgkStatucChi(String tqgkStatucChi) {
		this.tqgkStatucChi = tqgkStatucChi;
	}

	public String getFjStatucChi() {
		return fjStatucChi;
	}

	public void setFjStatucChi(String fjStatucChi) {
		this.fjStatucChi = fjStatucChi;
	}

	public String getFmrInfo() {
		return fmrInfo;
	}

	public void setFmrInfo(String fmrInfo) {
		this.fmrInfo = fmrInfo;
	}

	public String getFmrICard() {
		return fmrICard;
	}

	public void setFmrICard(String fmrICard) {
		this.fmrICard = fmrICard;
	}

	public List<SqrJson> getSqrList() {
		return sqrList;
	}

	public void setSqrList(List<SqrJson> sqrList) {
		this.sqrList = sqrList;
	}

	public String getWts() {
		return wts;
	}

	public void setWts(String wts) {
		this.wts = wts;
	}

	public String getZlTitle() {
		return zlTitle;
	}

	public void setZlTitle(String zlTitle) {
		this.zlTitle = zlTitle;
	}

	public String getZlSerialNo() {
		return zlSerialNo;
	}

	public void setZlSerialNo(String zlSerialNo) {
		this.zlSerialNo = zlSerialNo;
	}
	
	
}
