package com.nam.crm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="PCFB")
public class PCFB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7680313762020076875L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	@Column(name="Customer_Id", unique=true, nullable=false)
	private String customer_id;
	
	@NotEmpty
	@Column(name="Customer_feedback", nullable=false)
	private String customer_feedback;
	
	@NotEmpty
	@Column(name="Customer_feedback_time", nullable=false)
	private String customer_feedback_time;
	
	@NotEmpty
	@Column(name="Customer_feedback_star", nullable=false)
	private String customer_feedback_star;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getCustomer_feedback_star() {
		return customer_feedback_star;
	}

	public void setCustomer_feedback_star(String customer_feedback_star) {
		this.customer_feedback_star = customer_feedback_star;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		if (!(obj instanceof PCFB))
			return false;
		PCFB other = (PCFB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FB [id=" + id + ", cid=" + customer_id + ", cfb=" + customer_feedback
				+ ", cfbt=" + customer_feedback_time + ", cfbs=" + customer_feedback_star
				+ "]";
	}
	
}
