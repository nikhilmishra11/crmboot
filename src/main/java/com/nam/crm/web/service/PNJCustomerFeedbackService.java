package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PNJCustomerFeedback;


public interface PNJCustomerFeedbackService {
	
	PNJCustomerFeedback findById(String id);
    void save(PNJCustomerFeedback pnjCustomerFeedback);
	void deleteById(int id);
	void updatePNJCustomerFeedback(PNJCustomerFeedback pnjCustomerFeedback);
	List<PNJCustomerFeedback> find_All_PNJCustomerFeedbackDetails();
	List<PNJCustomerFeedback> find_All_PNJCustomerFeedbackDetails(String id);

}
