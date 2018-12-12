package com.nam.crm.model.jpa;

public class NoSaleObject {

	private int no_sale_count;
	private String sale_agent;
	
	public NoSaleObject(int no_sale_count, String sale_agent) {
		super();
		this.no_sale_count = no_sale_count;
		this.sale_agent = sale_agent;
	}

	public int getNo_sale_count() {
		return no_sale_count;
	}

	public void setNo_sale_count(int no_sale_count) {
		this.no_sale_count = no_sale_count;
	}

	public String getSale_agent() {
		return sale_agent;
	}

	public void setSale_agent(String sale_agent) {
		this.sale_agent = sale_agent;
	}
	

	
}
