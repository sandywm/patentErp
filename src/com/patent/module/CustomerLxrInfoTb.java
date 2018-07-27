package com.patent.module;

/**
 * CustomerLxrInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CustomerLxrInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CustomerInfoTb customerInfoTb;
	private String cusLxrName;
	private String cusLxrTel;
	private String cusLxrEmail;

	// Constructors

	/** default constructor */
	public CustomerLxrInfoTb() {
	}

	/** minimal constructor */
	public CustomerLxrInfoTb(CustomerInfoTb customerInfoTb, String cusLxrName,
			String cusLxrTel, String cusLxrEmail) {
		this.customerInfoTb = customerInfoTb;
		this.cusLxrName = cusLxrName;
		this.cusLxrTel = cusLxrTel;
		this.cusLxrEmail = cusLxrEmail;
	}

	/** full constructor */
	public CustomerLxrInfoTb(Integer id,CustomerInfoTb customerInfoTb, String cusLxrName,
			String cusLxrTel, String cusLxrEmail) {
		this.id = id;
		this.customerInfoTb = customerInfoTb;
		this.cusLxrName = cusLxrName;
		this.cusLxrTel = cusLxrTel;
		this.cusLxrEmail = cusLxrEmail;
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

	public String getCusLxrName() {
		return this.cusLxrName;
	}

	public void setCusLxrName(String cusLxrName) {
		this.cusLxrName = cusLxrName;
	}

	public String getCusLxrTel() {
		return this.cusLxrTel;
	}

	public void setCusLxrTel(String cusLxrTel) {
		this.cusLxrTel = cusLxrTel;
	}

	public String getCusLxrEmail() {
		return this.cusLxrEmail;
	}

	public void setCusLxrEmail(String cusLxrEmail) {
		this.cusLxrEmail = cusLxrEmail;
	}

}