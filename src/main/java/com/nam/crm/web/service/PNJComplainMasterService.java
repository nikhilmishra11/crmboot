package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PNJComplainMaster;


public interface PNJComplainMasterService {
	
	PNJComplainMaster findById(String id);
    void save(PNJComplainMaster pnjComplainMaster);
	void deleteById(int id);
	void updatePNJComplainMaster(PNJComplainMaster pnjComplainMaster);
	void updatePNJComplainMaster(PNJComplainMaster pnjComplainMaster,String user_type);
	
	void updatePNJComplainMasterFeedBack(PNJComplainMaster pnjComplainMaster);
	
	List<PNJComplainMaster> find_All_PNJComplainMasterDetails();
	List<PNJComplainMaster> find_All_PNJComplainMasterDetails(String id);

	List<PNJComplainMaster> find_All_User_WisePNJComplainMaster(String id,String type);
	List<PNJComplainMaster> find_All_User_WisePNJComplainMasterForTech(String tech);
	List<Object> AllMasterDeatils();
	
	
	List<PNJComplainMaster> Serach_All_Complain_Details(String agent_name);
}
