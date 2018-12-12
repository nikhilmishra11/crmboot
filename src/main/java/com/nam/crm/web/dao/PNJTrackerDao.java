package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.PNJDailyTracker;

public interface PNJTrackerDao {
	
	PNJDailyTracker findById(String customerName);
	void save(PNJDailyTracker pnjDailyTracker);
	List<PNJDailyTracker> find_All_TrackerDetails();
	List<PNJDailyTracker> find_All_TrackerDetails(String name);

}
