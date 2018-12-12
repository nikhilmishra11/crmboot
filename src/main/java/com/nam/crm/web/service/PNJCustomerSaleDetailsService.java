package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.NoSaleObject;
import com.nam.crm.web.model.PNJCustomerSaleMaster;
import com.nam.crm.web.model.SaleObject;

public interface PNJCustomerSaleDetailsService {

	PNJCustomerSaleMaster findById(String customerId);
	void save(PNJCustomerSaleMaster pnjCustomerSaleMaster);
	void deleteByCustomerId(String customerId);
	void updatePNJCustomerSaleMaster(PNJCustomerSaleMaster pnjComplainMaster,String user_type);
	List<PNJCustomerSaleMaster> find_All_User_WisePNJComplainMaster(String id,String type);
	List<PNJCustomerSaleMaster> find_All_PNJSaleMasterDetails();
	List<PNJCustomerSaleMaster> find_All_User_WisePNJSaleMasterForTech(String tech);
	List<PNJCustomerSaleMaster> find_All_PNJSaleMaster(String details_filled_by);
	
	long countSaleResult();
	long countSaleResult(String customer_Id);
	
	List<SaleObject> saleCount();
	List<NoSaleObject> noSaleCount();
	
	List<SaleObject> dailySaleCount();
	List<NoSaleObject> dailyNoSaleCount();
	
}
