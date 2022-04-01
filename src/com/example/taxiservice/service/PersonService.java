package com.example.taxiservice.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.PersonDao;
import com.example.taxiservice.model.Person;

public class PersonService {
	
	private static final String PHONE_PATTERN = "[0-9]{10}";
	private static final String TEXT_PATTERN = "[A-Z][a-z]+|[А-Я][а-я]+";
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
	
	private PersonDao personDao;

	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public Person find(String phone) {
		return personDao.find(phone);
	}
	
	public Person find(Long id) {
		return personDao.find(id);
	}
	
	public boolean insert(Person person) {
		return personDao.insert(person);
	}
	
	public boolean update(Person person) {
		boolean result = false;
		
		if(person.getPassword().isEmpty()) {
			Person oldPerson = personDao.find(person.getId());
			person.setPassword(oldPerson.getPassword());
			result = personDao.update(person);
		}else {
			result = personDao.update(person);
		}
		
		return result;
		
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
			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
			Matcher matcher = pattern.matcher(password);
			
			if(matcher.matches()) {
				result = true;
			}

		}
		
		return result;
	}
	
	public boolean validatePassword(String password, String passwordConfirm) {
		boolean result = false;

		if(password.equals(passwordConfirm)) {
			result = true;
		}
		
		return result;	
	}
	
	public String hashPassword(String password) {
		String result = null;
		
		if(password != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] bytes = md.digest(password.getBytes());
	            StringBuilder sb = new StringBuilder();
	            
	            for (int i = 0; i < bytes.length; i++) {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            
	            result = sb.toString();
	            
	        } catch (NoSuchAlgorithmException e) {
	            LOG.error(e.getMessage());
	        }
		}
		
		return result;	
	}	

}
