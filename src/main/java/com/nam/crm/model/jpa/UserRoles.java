/**
 * 
 */
package com.nam.crm.model.jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Nikhil 08-Sep-2018
 */

@ToString
@Data
@Entity
@Table(name = "user_roles")
@EqualsAndHashCode
public class UserRoles {

	@EmbeddedId 
	private UserRolesPk userRolesPk;
}
