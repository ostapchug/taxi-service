package com.example.taxiservice.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.PersonDao;
import com.example.taxiservice.dao.mysql.MySqlPersonDao;
import com.example.taxiservice.model.Person;

public class PersonService {
	
	private static final String PHONE_PATTERN = "[0-9]{10}";
	private static final String TEXT_PATTERN = "[A-Z][A-Za-z]+|[А-Я][А-Яа-я]+";
//	private static final String PASSWORD_PATTERN = "";
	
	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
	
	private PersonDao personDao;

	public PersonService() {
		this.personDao = new MySqlPersonDao(DBManager.getInstance());
	}
	
	public Person find(String phone) {
		return personDao.find(phone);
	}
	
	public Person find(Long id) {
		return personDao.find(id);
	}
	
	public void insert(Person person) {
		personDao.insert(person);
	}
	
	public void update(Person person) {
		Person oldPerson = personDao.find(person.getId());
		
		if(person.getPassword().isEmpty()) {
			person.setPassword(oldPerson.getPassword());
			personDao.update(person);
		}else {
			personDao.update(person);
		}
		
	}
	
	public void delete(Person person) {
		personDao.delete(person);
	}
	
	public boolean validateText(String text) {
		boolean result = false;
		
		Pattern pattern = Pattern.compile(TEXT_PATTERN);
		Matcher matcher = pattern.matcher(text);
		
		if(matcher.matches()) {
			result = true;
		}
		
		return result;
	}
	
	public boolean validatePhone(String phone) {
		boolean result = false;
		
		if(phone != null) {
			Pattern pattern = Pattern.compile(PHONE_PATTERN);
			Matcher matcher = pattern.matcher(phone);

			if(matcher.matches()) {
				result = true;
			}
		}
						
		return result;	
	}
	
	public boolean validatePassword(String password) {
		boolean result = false;
		
		if(password != null) {
			result = true;

		}
		
		return result;
	}
	
	public boolean validatePassword(String password, String passwordConfirm) {
		boolean result = false;
		LOG.debug(password + passwordConfirm);
		if(password.equals(passwordConfirm)) {
			result = true;
		}
		
		return result;	
	}
	
	public String hashPassword(String password) {
		String result = null;
		result = password; 		
		return result;	
	}	

}
