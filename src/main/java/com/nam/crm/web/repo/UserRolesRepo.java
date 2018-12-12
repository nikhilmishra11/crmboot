package com.nam.crm.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nam.crm.model.jpa.UserRoles;

public interface UserRolesRepo extends JpaRepository<UserRoles, String> {
}
