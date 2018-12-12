package com.nam.crm.model.jpa;

public class SaleObject {
	
	private int sale_count;
	private String sale_agent;
	
	public SaleObject(int sale_count, String sale_agent) {
		super();
		this.sale_count = sale_count;
		this.sale_agent = sale_agent;
	}

	public int getSale_count() {
		return sale_count;
	}

	public void setSale_count(int sale_count) {
		this.sale_count = sale_count;
	}

	public String getSale_agent() {
		return sale_agent;
	}

	public void setSale_agent(String sale_agent) {
		this.sale_agent = sale_agent;
	}
	
	public String toString(){
		return "["+sale_count+","+sale_agent+"]";
	}
	
	
}
