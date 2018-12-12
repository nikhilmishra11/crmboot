package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PNJCustomerFeedback;


public interface PNJCustomerFeedbackDao {
	
	PNJCustomerFeedback findById(String customerId);
	void save(PNJCustomerFeedback pnjCustomerFeedback);
	void deleteByCustomerId(String customerId);
	List<PNJCustomerFeedback> find_All_PNJCustomerFeedbackDetails();
	List<PNJCustomerFeedback> find_All_PNJCustomerFeedbackDetails(String customerId);
	

}
