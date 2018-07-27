package com.patent.module;

/**
 * CustomerFmrInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CustomerFmrInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CustomerInfoTb customerInfoTb;
	private String cusFmrName;
	private String cusFmrICard;
	private String cusFmrTel;
	private String cusFxrEmail;

	// Constructors

	/** default constructor */
	public CustomerFmrInfoTb() {
	}

	/** minimal constructor */
	public CustomerFmrInfoTb(CustomerInfoTb customerInfoTb, String cusFmrName,
			String cusFmrICard, String cusFmrTel, String cusFxrEmail) {
		this.customerInfoTb = customerInfoTb;
		this.cusFmrName = cusFmrName;
		this.cusFmrICard = cusFmrICard;
		this.cusFmrTel = cusFmrTel;
		this.cusFxrEmail = cusFxrEmail;
	}

	/** full constructor */
	public CustomerFmrInfoTb(Integer id,CustomerInfoTb customerInfoTb, String cusFmrName,
			String cusFmrICard, String cusFmrTel, String cusFxrEmail) {
		this.id = id;
		this.customerInfoTb = customerInfoTb;
		this.cusFmrName = cusFmrName;
		this.cusFmrICard = cusFmrICard;
		this.cusFmrTel = cusFmrTel;
		this.cusFxrEmail = cusFxrEmail;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerInfoTb getCustomerInfoTb() {
		return this.customerInfoTb;
	}

	public void setCustomerInfoTb(CustomerInfoTb customerInfoTb) {
		this.customerInfoTb = customerInfoTb;
	}

	public String getCusFmrName() {
		return this.cusFmrName;
	}

	public void setCusFmrName(String cusFmrName) {
		this.cusFmrName = cusFmrName;
	}

	public String getCusFmrICard() {
		return this.cusFmrICard;
	}

	public void setCusFmrICard(String cusFmrICard) {
		this.cusFmrICard = cusFmrICard;
	}

	public String getCusFmrTel() {
		return this.cusFmrTel;
	}

	public void setCusFmrTel(String cusFmrTel) {
		this.cusFmrTel = cusFmrTel;
	}

	public String getCusFxrEmail() {
		return this.cusFxrEmail;
	}

	public void setCusFxrEmail(String cusFxrEmail) {
		this.cusFxrEmail = cusFxrEmail;
	}

}