/**
 * 
 */
package com.patent.module;

/**  
 *  @author  你的名字  
 *  @ClassName  : CusBackFeeInfo  
 *  @Version  版本   
 *  @ModifiedBy 修改人  
 *  @Copyright  公司名称  
 *  @date  2018-12-15 下午09:26:11 
 */

public class CusBackFeeInfo {
	private Integer id;
	private String backFeePrice;
	private String backDate;
	private String backType;
	private CustomerInfoTb cus;
	private CpyInfoTb cpy;
	private CpyUserInfo user;
	private String operateTime;
	private String remark;

	public CusBackFeeInfo(String backFeePrice, String backDate,
			String backType, CustomerInfoTb cus, CpyInfoTb cpy,
			CpyUserInfo user, String operateTime, String remark) {
		this.backFeePrice = backFeePrice;
		this.backDate = backDate;
		this.backType = backType;
		this.cus = cus;
		this.cpy = cpy;
		this.user = user;
		this.operateTime = operateTime;
		this.remark = remark;
	}
	
	public CusBackFeeInfo(Integer id, String backFeePrice, String backDate,
			String backType, CustomerInfoTb cus, CpyInfoTb cpy,
			CpyUserInfo user, String operateTime, String remark) {
		this.id = id;
		this.backFeePrice = backFeePrice;
		this.backDate = backDate;
		this.backType = backType;
		this.cus = cus;
		this.cpy = cpy;
		this.user = user;
		this.operateTime = operateTime;
		this.remark = remark;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBackFeePrice() {
		return backFeePrice;
	}
	public void setBackFeePrice(String backFeePrice) {
		this.backFeePrice = backFeePrice;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	public String getBackType() {
		return backType;
	}
	public void setBackType(String backType) {
		this.backType = backType;
	}
	public CustomerInfoTb getCus() {
		return cus;
	}
	public void setCus(CustomerInfoTb cus) {
		this.cus = cus;
	}
	public CpyInfoTb getCpy() {
		return cpy;
	}
	public void setCpy(CpyInfoTb cpy) {
		this.cpy = cpy;
	}
	public CpyUserInfo getUser() {
		return user;
	}
	public void setUser(CpyUserInfo user) {
		this.user = user;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
