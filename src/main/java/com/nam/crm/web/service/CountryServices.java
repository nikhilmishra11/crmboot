package com.nam.crm.web.service;

import java.util.List;

import com.nam.crm.web.model.Country;

public interface CountryServices {

	Country findById(int id);
    void save(Country country);
	void deleteById(int id);
	void updateUser(Country country);
	List<Country> findAllCountries();
}
