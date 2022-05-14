package com.example.taxiservice.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.PersonDao;
import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Person;

/**
 * Service layer for Person DAO object.
 */
@Singleton
public class PersonService {

	private static final String PHONE_PATTERN = "[0-9]{10}";
	private static final String TEXT_PATTERN = "[A-Z][a-z]+|[\\p{IsCyrillic}&&\\p{Lu}][\\p{IsCyrillic}&&\\p{Ll}]+";
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

	@InjectByType
	private PersonDao personDao;

	public PersonService() {
		LOG.info("PersonService initialized");
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

		if (person.getPassword().isEmpty()) {
			Person oldPerson = personDao.find(person.getId());
			person.setPassword(oldPerson.getPassword());
			result = personDao.update(person);
		} else {
			result = personDao.update(person);
		}

		return result;

	}

	public static boolean validateText(String text) {
		boolean result = false;

		if (text != null) {
			Pattern pattern = Pattern.compile(TEXT_PATTERN);
			Matcher matcher = pattern.matcher(text);

			if (matcher.matches()) {
				result = true;
			}
		}

		return result;
	}

	public static boolean validatePhone(String phone) {
		boolean result = false;

		if (phone != null) {
			Pattern pattern = Pattern.compile(PHONE_PATTERN);
			Matcher matcher = pattern.matcher(phone);

			if (matcher.matches()) {
				result = true;
			}
		}

		return result;
	}

	public static boolean validatePassword(String password) {
		boolean result = false;

		if (password != null) {
			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
			Matcher matcher = pattern.matcher(password);

			if (matcher.matches()) {
				result = true;
			}

		}

		return result;
	}

	public static boolean validatePasswordConfirm(String password, String passwordConfirm) {
		boolean result = false;

		if (password != null && password.equals(passwordConfirm)) {
			result = true;
		}

		return result;
	}

	public static String hashPassword(String password) {
		String result = null;

		if (password != null) {
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
