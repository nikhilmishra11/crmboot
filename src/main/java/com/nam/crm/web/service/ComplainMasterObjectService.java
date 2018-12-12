package com.nam.crm.web.service;

import java.util.ArrayList;
import java.util.List;

import com.nam.crm.web.model.ComplainMasterObject;


public interface ComplainMasterObjectService {

	ArrayList<ComplainMasterObject> findAllMasterDetails();
	List<ComplainMasterObject> getAllData();
}
