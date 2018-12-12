package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PCFB;

public interface PCFBService {
	PCFB findById(int id);
	void savePCFB(PCFB pcfb);
	void updatePCFB(PCFB pcfb);
	List<PCFB> findAllPCFB(); 
}
