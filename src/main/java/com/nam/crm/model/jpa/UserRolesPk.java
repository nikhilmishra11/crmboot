package com.nam.crm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Data
@Embeddable
@EqualsAndHashCode
public class UserRolesPk implements Serializable {

	private static final long serialVersionUID = 9104942968320712217L;
	
	@Column(name = "user_id")
	private String userId;
	@Column(name = "role_name")
	private String role;
	
	public UserRolesPk() {}

	public UserRolesPk(String userId, String role) {
		super();
		this.userId = userId;
		this.role = role;
	}
	
}