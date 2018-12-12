package com.nam.crm.model.jpa;


public class FeedbackObject {
	
	private String customer_id;
	
	private String complain_id;
	
	private String customer_feedback;

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getComplain_id() {
		return complain_id;
	}

	public void setComplain_id(String complain_id) {
		this.complain_id = complain_id;
	}

	public String getCustomer_feedback() {
		return customer_feedback;
	}

	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
	}
	
	public String toString() {
		return customer_id+"-"+complain_id+"-"+customer_feedback;
	}

}
