package com.patent.json;

public class LxrJson {
	private String lxrName;
	private String lxrMobile;
	private String lxrEmail;
	
	public LxrJson(){}
	
	public LxrJson(String lxrName, String lxrMobile, String lxrEmail) {
		this.lxrName = lxrName;
		this.lxrMobile = lxrMobile;
		this.lxrEmail = lxrEmail;
	}

	public String getLxrName() {
		return lxrName;
	}

	public void setLxrName(String lxrName) {
		this.lxrName = lxrName;
	}

	public String getLxrMobile() {
		return lxrMobile;
	}

	public void setLxrMobile(String lxrMobile) {
		this.lxrMobile = lxrMobile;
	}

	public String getLxrEmail() {
		return lxrEmail;
	}

	public void setLxrEmail(String lxrEmail) {
		this.lxrEmail = lxrEmail;
	}
	
}
