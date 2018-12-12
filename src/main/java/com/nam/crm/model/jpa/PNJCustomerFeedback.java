package com.nam.crm.model.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pnj_customer_feedback")
public class PNJCustomerFeedback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4956631317576943111L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id; 
	
	@Column(name="customer_id", unique=true, nullable=false)
	private String customer_id;
	
	@Column(name="customer_feedback", nullable=false)
	private String customer_feedback;
	
	@Column(name="customer_feedback_time", nullable=false)
	private String customer_feedback_time;
	
	@Column(name="complain_id", nullable=false)
	private String complain_id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_feedback() {
		return customer_feedback;
	}

	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
	}

	public String getCustomer_feedback_time() {
		return customer_feedback_time;
	}

	public void setCustomer_feedback_time(String customer_feedback_time) {
		this.customer_feedback_time = customer_feedback_time;
	}

	public String getComplain_id() {
		return complain_id;
	}

	public void setComplain_id(String complain_id) {
		this.complain_id = complain_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PNJCustomerFeedback))
			return false;
		PNJCustomerFeedback other = (PNJCustomerFeedback) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String toString() {
		return customer_id+"-"+complain_id+"-"+customer_feedback+"-"+customer_feedback_time;
	}

}
