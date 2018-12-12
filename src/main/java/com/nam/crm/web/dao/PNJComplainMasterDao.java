package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PNJComplainMaster;


public interface PNJComplainMasterDao {
	
	PNJComplainMaster findById(String customerId);
	
	void save(PNJComplainMaster pnjComplainMaster);
	void deleteByCustomerId(String customerId);
	List<PNJComplainMaster> find_All_PNJComplainMaster();
	List<PNJComplainMaster> find_All_PNJComplainMaster(String id);
	List<PNJComplainMaster> findById(String customerId,String serviceId);
	
	List<PNJComplainMaster> find_All_User_WisePNJComplainMaster(String id,String type);
	List<PNJComplainMaster> find_All_User_WisePNJComplainMasterForTech(String type);
	
	
	
	List<PNJComplainMaster> Serach_All_Complain_Details(String agent_name);
	
	
	List<Object> AllMasterDeatils();
	
}
