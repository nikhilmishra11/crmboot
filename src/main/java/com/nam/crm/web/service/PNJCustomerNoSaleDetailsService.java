package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PNJCustomerNoSaleDetails;

public interface PNJCustomerNoSaleDetailsService {

	PNJCustomerNoSaleDetails findById(String customerId);
	void save(PNJCustomerNoSaleDetails pnjComplainMaster);
	void deleteByCustomerId(String customerId);
	List<PNJCustomerNoSaleDetails> find_All_PNJNoSaleMasterDetails();
	
	long countNoSaleResult();
	long countNoSaleResult(String customerId);
}
