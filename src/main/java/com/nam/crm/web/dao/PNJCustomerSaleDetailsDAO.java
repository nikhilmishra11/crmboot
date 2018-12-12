package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.NoSaleObject;
import com.nam.crm.web.model.PNJCustomerSaleMaster;
import com.nam.crm.web.model.SaleObject;

public interface PNJCustomerSaleDetailsDAO {
	
	PNJCustomerSaleMaster findById(String customerId);
	void save(PNJCustomerSaleMaster pnjCustomerSaleMaster);	
	List<PNJCustomerSaleMaster> find_All_PNJSaleMasterDetails();
	List<PNJCustomerSaleMaster> find_All_PNJSaleMaster(String details_filled_by);
	List<PNJCustomerSaleMaster> find_All_User_WisePNJSaleMaster(String id,String type);
	List<PNJCustomerSaleMaster> find_All_User_WisePNJSaleMasterForTech(String type);

	long countSaleResult();
	long countSaleResult(String customer_Id);
	
	List<SaleObject> saleCount();
	List<SaleObject> dailySaleCount();
	
	List<NoSaleObject> noSaleCount();
	List<NoSaleObject> dailyNoSaleCount();
	
}
