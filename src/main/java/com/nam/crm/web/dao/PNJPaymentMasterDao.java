package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PNJPaymentMaster;


public interface PNJPaymentMasterDao {
	
	PNJPaymentMaster findById(int customerId);
	void save(PNJPaymentMaster pnjPaymentMaster);
	void deleteByCustomerId(String customerId);
	List<PNJPaymentMaster> find_All_PNJPaymentDeatils();

}
