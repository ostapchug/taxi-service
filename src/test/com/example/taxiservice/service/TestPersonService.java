package test.com.example.taxiservice.service;

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
public class TestPersonService {
	
	@Mock
	private PersonDao personDao;
	
	@InjectMocks
	private PersonService personService;
	
	private Person person;

	@Before
	public void setUp() throws Exception {
		person = new Person();
		person.setId(3L);
		person.setPhone("0123456781");
		person.setPassword("Client#1");
		person.setName("Jane");
		person.setSurname("Doe");		
		
		when(personDao.find(3L)).thenReturn(person);
		when(personDao.find("0123456781")).thenReturn(person);
		when(personDao.insert(person)).thenReturn(true);
		when(personDao.insert(new Person())).thenReturn(false);
		when(personDao.update(person)).thenReturn(true);
//		when(personDao.update(new Person())).thenReturn(false);
	}

	@Test
	public void testFindById() {
		Person result = personService.find(3L);
		assertEquals(person, result);
	}
	
	@Test
	public void testFindByPhone() {
		Person result = personService.find("0123456781");
		assertEquals(person, result);
	}
	
	@Test
	public void testInsert() {
		assertTrue(personService.insert(person));
	}
	
	@Test
	public void testInsertEmpty() {
		assertFalse(personService.insert(new Person()));
	}
	
	@Test
	public void testUpdate() {
		assertTrue(personService.update(person));
	}
	
//	@Test
	public void testUpdateEmpty() {
		assertFalse(personService.update(new Person()));
	}

}
