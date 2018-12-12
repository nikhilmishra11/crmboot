package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PCFB;

public interface PCFBDao {

	PCFB findById(int id);
	
	void save(PCFB pcfb);
	
	void deleteById(int id);
	
	List<PCFB> findAllUsers();
}
