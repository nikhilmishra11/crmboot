package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.PNJDailyTracker;

public interface PNJTrackerService {
	
	PNJDailyTracker findById(String customerName);
	void save(PNJDailyTracker pnjDailyTracker);
	List<PNJDailyTracker> find_All_TrackerDetails();
	List<PNJDailyTracker> find_All_TrackerDetails(String name);


}
