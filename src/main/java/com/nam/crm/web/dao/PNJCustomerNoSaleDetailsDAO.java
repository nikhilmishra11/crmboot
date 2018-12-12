package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PNJCustomerNoSaleDetails;

public interface PNJCustomerNoSaleDetailsDAO {
	
	PNJCustomerNoSaleDetails findById(String customerId);
	void save(PNJCustomerNoSaleDetails pnjComplainMaster);
	List<PNJCustomerNoSaleDetails> find_All_PNJNoSaleMasterDetails();
	long countNoSaleResult();
	long countNoSaleResult(String customerId);

}
