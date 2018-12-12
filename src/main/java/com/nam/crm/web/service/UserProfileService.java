package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.UserProfile;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}
