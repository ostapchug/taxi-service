package com.example.taxiservice.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.taxiservice.dao.PersonDao;
import com.example.taxiservice.model.Person;
import com.example.taxiservice.service.PersonService;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

	@Mock
	private PersonDao personDao;

	@InjectMocks
	private PersonService personService;

	private Person validPerson, invalidPerson;

	private String validName = "Tom", invalidName = "tom";

	private String validPhone = "0123456781", invalidPhone = "012345678";

	private String validPassword = "Client#1", invalidPassword = "client1";

	private String hash = "7b9c03298c85e20c45d2e3128e0c28a0a2b72c421277335ede1d3ad688692be6";

	@Before
	public void setUp() throws Exception {
		validPerson = new Person();
		validPerson.setId(3L);
		validPerson.setPhone("0123456781");
		validPerson.setPassword("Client#1");

		invalidPerson = new Person();
		invalidPerson.setPassword("Client#1");

		when(personDao.find(3L)).thenReturn(validPerson);
		when(personDao.find("0123456781")).thenReturn(validPerson);
		when(personDao.insert(validPerson)).thenReturn(true);
		when(personDao.insert(invalidPerson)).thenReturn(false);
		when(personDao.update(validPerson)).thenReturn(true);
		when(personDao.update(invalidPerson)).thenReturn(false);
	}

	@Test
	public void testFindById() {
		Person result = personService.find(validPerson.getId());
		assertEquals(validPerson, result);
	}

	@Test
	public void testFindByPhone() {
		Person result = personService.find(validPerson.getPhone());
		assertEquals(validPerson, result);
	}

	@Test
	public void testInsert() {
		assertTrue(personService.insert(validPerson));
	}

	@Test
	public void testInsertFalse() {
		assertFalse(personService.insert(invalidPerson));
	}

	@Test
	public void testUpdate() {
		assertTrue(personService.update(validPerson));
	}

	@Test
	public void testUpdateFalse() {
		assertFalse(personService.update(invalidPerson));
	}

	@Test
	public void testValidateText() {
		assertTrue(PersonService.validateText(validName));
	}

	@Test
	public void testValidateTextFalse() {
		assertFalse(PersonService.validateText(invalidName));
	}

	@Test
	public void testValidateTextNull() {
		assertFalse(PersonService.validateText(null));
	}

	@Test
	public void testValidatePhone() {
		assertTrue(PersonService.validatePhone(validPhone));
	}

	@Test
	public void testValidatePhoneFalse() {
		assertFalse(PersonService.validatePhone(invalidPhone));
	}

	@Test
	public void testValidatePhoneNull() {
		assertFalse(PersonService.validatePhone(null));
	}

	@Test
	public void testValidatePassword() {
		assertTrue(PersonService.validatePassword(validPassword));
	}

	@Test
	public void testValidatePasswordFalse() {
		assertFalse(PersonService.validatePassword(invalidPassword));
	}

	@Test
	public void testValidatePasswordNull() {
		assertFalse(PersonService.validatePassword(null));
	}

	@Test
	public void testValidatePasswordConfirm() {
		assertTrue(PersonService.validatePasswordConfirm(validPassword, validPassword));
	}

	@Test
	public void testValidatePasswordConfirmFalse() {
		assertFalse(PersonService.validatePasswordConfirm(validPassword, invalidPassword));
	}

	@Test
	public void testValidatePasswordConfirmNull() {
		assertFalse(PersonService.validatePasswordConfirm(null, null));
	}

	@Test
	public void testHashPassword() {
		String actual = PersonService.hashPassword(validPassword);
		assertEquals(hash, actual);
	}

	@Test
	public void testHashPasswordFalse() {
		String actual = PersonService.hashPassword(invalidPassword);
		assertNotEquals(hash, actual);
	}

	@Test
	public void testHashPasswordNull() {
		String actual = PersonService.hashPassword(null);
		assertNull(actual);
	}

}
