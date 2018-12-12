package com.nam.crm.model.jpa;

public class SearchObject {
	
	private String customer_id;
	private String complain_id;
	private String customer_name;
	private String no_sale;
	
	public String getNo_sale() {
		return no_sale;
	}

	public void setNo_sale(String no_sale) {
		this.no_sale = no_sale;
	}

	public String getComplain_id() {
		return complain_id;
	}

	public void setComplain_id(String complain_id) {
		this.complain_id = complain_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	

}
