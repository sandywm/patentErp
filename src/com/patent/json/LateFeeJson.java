package com.patent.json;

/**
 * 年费滞纳金JSON
 * @author Administrator
 * @createDate 2018-11-4
 */
public class LateFeeJson {
	
	private Double lateFee;
	private String feeSDate;
	private String feeEDate;
	
	public LateFeeJson(){
		
	}
	
	public LateFeeJson(Double lateFee, String feeSDate, String feeEDate) {
		this.lateFee = lateFee;
		this.feeSDate = feeSDate;
		this.feeEDate = feeEDate;
	}

	public Double getLateFee() {
		return lateFee;
	}

	public void setLateFee(Double lateFee) {
		this.lateFee = lateFee;
	}

	public String getFeeSDate() {
		return feeSDate;
	}

	public void setFeeSDate(String feeSDate) {
		this.feeSDate = feeSDate;
	}

	public String getFeeEDate() {
		return feeEDate;
	}

	public void setFeeEDate(String feeEDate) {
		this.feeEDate = feeEDate;
	}
	
	
}
