package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.UserProfile;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
