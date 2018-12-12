package com.nam.crm.web.dao;

import java.util.List;

import com.nam.crm.web.model.Country;

public interface CountryDao {
	
	Country findById(int id);
    void save(Country country);
	void deleteById(int id);
	List<Country> findAllCountries();

}
