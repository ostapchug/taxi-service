package com.example.taxiservice.dao;

import com.example.taxiservice.model.Person;

public interface PersonDao extends EntityDao<Person> {
	
	/**
	 * Finds Person in DB by specified phone
	 *
	 * @param phone - person phone
	 * 
	 * @return Person instance with given phone
	 */
	Person find(String phone);
	
}
