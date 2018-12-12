package com.nam.crm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pnj_employee_tracker")
public class PNJDailyTracker implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique=true, nullable=false)
	private String id;
	
	@Column(name="emp_name", unique=true, nullable=false)
	private String emp_name;
	
	@Column(name="emp_type", unique=true, nullable=false)
	private String emp_type;
	
	@Column(name="emp_daily_tracker", unique=true, nullable=false)
	private String emp_daily_tracker;
	
	@Column(name="admin_tracker", unique=true, nullable=false)
	private String admin_tracker;
	
	@Column(name="tracker_date", unique=true, nullable=false)
	private String tracker_date;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

	public String getEmp_daily_tracker() {
		return emp_daily_tracker;
	}

	public void setEmp_daily_tracker(String emp_daily_tracker) {
		this.emp_daily_tracker = emp_daily_tracker;
	}

	public String getAdmin_tracker() {
		return admin_tracker;
	}

	public void setAdmin_tracker(String admin_tracker) {
		this.admin_tracker = admin_tracker;
	}

	public String getTracker_date() {
		return tracker_date;
	}

	public void setTracker_date(String tracker_date) {
		this.tracker_date = tracker_date;
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
		if (!(obj instanceof PNJDailyTracker))
			return false;
		PNJDailyTracker other = (PNJDailyTracker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
