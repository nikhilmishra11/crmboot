package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.User;


public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void save(User user);
	
	void deleteBySSO(String sso);
	
	List<User> findAllUsers();
	
	List<String> listDataString();

}

