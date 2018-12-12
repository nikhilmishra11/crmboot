package com.nam.crm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pnj_customer_details_master")
public class PNJCustomerDetailsMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4628853415724766799L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id;
	
	
	@Column(name="customer_id", unique=true, nullable=false)
	private String customer_id;
	
	@Column(name="customer_name", nullable=false)
	private String customer_name;
	
	@Column(name="customer_email_id", nullable=false)
	private String customer_email_id;
	
	@Column(name="customer_contact_no", nullable=false)
	private String customer_contact_no;
	
	@Column(name="customer_contact_no_alternative", nullable=false)
	private String customer_contact_no_alternative;
	
	@Column(name="customer_address", nullable=false)
	private String customer_address;
	
//	@Column(name="customer_created_on",  nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Column(name="customer_created_on", nullable=false)
	private String customer_created_on;
	
	@Column(name="customer_remarks", nullable=false)
	private String customer_remarks;
	
	@Column(name="best_time_to_reach", nullable=false)
	private String best_time_to_reach;
	
	@Column(name="no_sale", nullable=false)
	private String no_sale;
	
	@Column(name="details_filled_by", nullable=false)
	private String details_filled_by;
	
	public String getDetails_filled_by() {
		return details_filled_by;
	}

	public void setDetails_filled_by(String details_filled_by) {
		this.details_filled_by = details_filled_by;
	}

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

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_email_id() {
		return customer_email_id;
	}

	public void setCustomer_email_id(String customer_email_id) {
		this.customer_email_id = customer_email_id;
	}

	public String getCustomer_contact_no() {
		return customer_contact_no;
	}

	public void setCustomer_contact_no(String customer_contact_no) {
		this.customer_contact_no = customer_contact_no;
	}

	public String getCustomer_contact_no_alternative() {
		return customer_contact_no_alternative;
	}

	public void setCustomer_contact_no_alternative(String customer_contact_no_alternative) {
		this.customer_contact_no_alternative = customer_contact_no_alternative;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_created_on() {
		return customer_created_on;
	}

	public void setCustomer_created_on(String customer_created_on) {
		this.customer_created_on = customer_created_on;
	}

	public String getCustomer_remarks() {
		return customer_remarks;
	}

	public void setCustomer_remarks(String customer_remarks) {
		this.customer_remarks = customer_remarks;
	}

	public String getBest_time_to_reach() {
		return best_time_to_reach;
	}

	public void setBest_time_to_reach(String best_time_to_reach) {
		this.best_time_to_reach = best_time_to_reach;
	}

	public String getNo_sale() {
		return no_sale;
	}

	public void setNo_sale(String no_sale) {
		this.no_sale = no_sale;
	}

	@Override
	
	public String toString() {
		return "id "+id+" Name "+customer_name+" Customer-id "+customer_id + "No sale "+no_sale;
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
		if (!(obj instanceof PNJCustomerDetailsMaster))
			return false;
		PNJCustomerDetailsMaster other = (PNJCustomerDetailsMaster) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
