package com.patent.module;

import java.util.HashSet;
import java.util.Set;

/**
 * CustomerInfoTb entity. @author MyEclipse Persistence Tools
 */

public class CustomerInfoTb implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CpyInfoTb cpyInfoTb;
	private String cusType;
	private String cusName;
	private String cusICard;
	private String cusAddress;
	private String cusZip;
	private Set<CustomerFmrInfoTb> customerFmrInfoTbs = new HashSet<CustomerFmrInfoTb>();
	private Set<CustomerLxrInfoTb> customerLxrInfoTbs = new HashSet<CustomerLxrInfoTb>();

	// Constructors

	/** default constructor */
	public CustomerInfoTb() {
	}

	/** minimal constructor */
	public CustomerInfoTb(CpyInfoTb cpyInfoTb,String cusType, String cusName, String cusICard,
			String cusAddress, String cusZip){
		this.cpyInfoTb = cpyInfoTb;
		this.cusType = cusType;
		this.cusName = cusName;
		this.cusICard = cusICard;
		this.cusAddress = cusAddress;
		this.cusZip = cusZip;
	}

	/** full constructor */
	public CustomerInfoTb(Integer id,CpyInfoTb cpyInfoTb,String cusType, String cusName, String cusICard,
			String cusAddress, String cusZip) {
		this.id = id;
		this.cpyInfoTb = cpyInfoTb;
		this.cusType = cusType;
		this.cusName = cusName;
		this.cusICard = cusICard;
		this.cusAddress = cusAddress;
		this.cusZip = cusZip;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCusType() {
		return this.cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusICard() {
		return this.cusICard;
	}

	public void setCusICard(String cusICard) {
		this.cusICard = cusICard;
	}

	public String getCusAddress() {
		return this.cusAddress;
	}

	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}

	public String getCusZip() {
		return this.cusZip;
	}

	public void setCusZip(String cusZip) {
		this.cusZip = cusZip;
	}

	public Set<CustomerFmrInfoTb> getCustomerFmrInfoTbs() {
		return customerFmrInfoTbs;
	}

	public void setCustomerFmrInfoTbs(Set<CustomerFmrInfoTb> customerFmrInfoTbs) {
		this.customerFmrInfoTbs = customerFmrInfoTbs;
	}

	public Set<CustomerLxrInfoTb> getCustomerLxrInfoTbs() {
		return customerLxrInfoTbs;
	}

	public void setCustomerLxrInfoTbs(Set<CustomerLxrInfoTb> customerLxrInfoTbs) {
		this.customerLxrInfoTbs = customerLxrInfoTbs;
	}

	public CpyInfoTb getCpyInfoTb() {
		return cpyInfoTb;
	}

	public void setCpyInfoTb(CpyInfoTb cpyInfoTb) {
		this.cpyInfoTb = cpyInfoTb;
	}


}