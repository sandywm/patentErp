package com.patent.json;

/**
 * 费用详情JSON
 * @author Administrator
 * @createDate 2018-11-4
 */
public class FeeDetailJson {
	
	private String feeName;
	private Double feeAmount;
	
	public FeeDetailJson(){
		
	}

	public FeeDetailJson(String feeName, Double feeAmount) {
		this.feeName = feeName;
		this.feeAmount = feeAmount;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	

}
