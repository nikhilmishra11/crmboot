
package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PNJCustomerDetailsMaster;


public interface PNJCustomerDetailsMasterDao {
	
	PNJCustomerDetailsMaster findById(String customerId);
	void save(PNJCustomerDetailsMaster pnjCustomerDetailsMaster);
	void deleteByCustomerId(String customerId);
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMaster1(String nosale_sale);
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMaster(String id);
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMasterUserwise(String id,String sale_nosale);
	List<PNJCustomerDetailsMaster> find_All_PNJCustomerDetailsMasterUserwise1(String id,String sale_nosale);
	List<PNJCustomerDetailsMaster> findCustomerName(String customerId);
	long countNoSaleResult();
	long countSaleResult();
	long countToDaysData();
	long countNoSaleResult(String customerId);
	long countSaleResult(String customerId);
	
	

}
