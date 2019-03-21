package com.patent.json;

import java.util.List;

public class SqrJson {

	private String sqrName;
	private String sqrICard;
	private String sqrAddress;
	private String sqrMobile;
	private List<LxrJson> lxrList;
	
	
	public SqrJson(){}

	public SqrJson(String sqrName, String sqrICard, String sqrAddress,
			String sqrMobile, List<LxrJson> lxrList) {
		this.sqrName = sqrName;
		this.sqrICard = sqrICard;
		this.sqrAddress = sqrAddress;
		this.sqrMobile = sqrMobile;
		this.lxrList = lxrList;
	}

	public String getSqrName() {
		return sqrName;
	}

	public void setSqrName(String sqrName) {
		this.sqrName = sqrName;
	}

	public String getSqrICard() {
		return sqrICard;
	}

	public void setSqrICard(String sqrICard) {
		this.sqrICard = sqrICard;
	}

	public String getSqrAddress() {
		return sqrAddress;
	}

	public void setSqrAddress(String sqrAddress) {
		this.sqrAddress = sqrAddress;
	}

	public String getSqrMobile() {
		return sqrMobile;
	}

	public void setSqrMobile(String sqrMobile) {
		this.sqrMobile = sqrMobile;
	}

	public List<LxrJson> getLxrList() {
		return lxrList;
	}

	public void setLxrList(List<LxrJson> lxrList) {
		this.lxrList = lxrList;
	}
	
	
	
}
