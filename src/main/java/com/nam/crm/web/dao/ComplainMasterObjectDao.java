package com.nam.crm.web.dao;

import java.util.ArrayList;
import java.util.List;

import com.nam.crm.web.model.ComplainMasterObject;

public interface ComplainMasterObjectDao {
	
	ArrayList<ComplainMasterObject> fetchAllData();
	List<ComplainMasterObject> getAllData();

}
