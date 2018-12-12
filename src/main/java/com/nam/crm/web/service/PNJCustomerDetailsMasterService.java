package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PNJCustomerDetailsMaster;


public interface PNJCustomerDetailsMasterService {
    
	PNJCustomerDetailsMaster findById(String id);
	void savePNJCustomerDetailsMaster(PNJCustomerDetailsMaster pnjCustomerDetailsMaster);
	void updatePNJCustomerDetailsMaster(PNJCustomerDetailsMaster pnjCustomerDetailsMaster);
	void deletePNJCustomerDetailsMaster(String customerId);
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMaster1(String nosale_sale); 
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMaster(String id); 
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMasterUserwise(String id,String nosale_sale);
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMasterUserwise1(String id,String nosale_sale);
	List<PNJCustomerDetailsMaster> findCustomerName(String customerId);
	long countNoSaleResult();
	long countSaleResult();
	long countNoSaleResult(String customerId);
	long countSaleResult(String customerId);
}
