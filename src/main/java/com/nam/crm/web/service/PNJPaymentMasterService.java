package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PNJPaymentMaster;


public interface PNJPaymentMasterService {
	
	PNJPaymentMaster findById(int id);
    void save(PNJPaymentMaster pnjPaymentMaster);
	void deleteById(int id);
	void updatePNJPaymentMaster(PNJPaymentMaster pnjPaymentMaster);
	List<PNJPaymentMaster> find_All_PNJPaymentMasterDetails();
	
}
