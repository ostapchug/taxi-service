package com.example.taxiservice.web.command.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.taxiservice.model.Person;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;

@RunWith(MockitoJUnitRunner.class)
public class ProfileUpdateCommandTest {
	
	@Mock 
	private HttpServletRequest request;
	@Mock 
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@Mock
	private PersonService personService;
	
	@InjectMocks
	private ProfileUpdateCommand profileUpdateCommand;
	
	private Person person;
	private String phone = "0123456781";
	private String validPassword = "Client#1", invalidPassword = "client#1";
	private String validName = "Tom", invalidName = "tom";
	private String validSurname = "Smith", invalidSurname = "smith";

	@Before
	public void setUp() throws Exception {
		person = new Person();
		person.setId(3L);
		person.setPhone("0123456781");
		person.setPassword("7b9c03298c85e20c45d2e3128e0c28a0a2b72c421277335ede1d3ad688692be6");
		person.setName(validName);
		person.setSurname(validSurname);
		person.setRoleId(1);
		
		when(request.getMethod()).thenReturn("POST");
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute("personId")).thenReturn(person.getId());
	}

	@Test
	public void testProfileUpdate() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(validPassword);
		when(request.getParameter("passwordConfirm")).thenReturn(validPassword);
		when(request.getParameter("name")).thenReturn(validName);
		when(request.getParameter("surname")).thenReturn(validSurname);
		when(personService.find(person.getId())).thenReturn(person);
		when(personService.find(person.getPhone())).thenReturn(person);
		when(personService.update(person)).thenReturn(true);
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, times(3)).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_PAGE, true), result);	
	}
	
	@Test
	public void testProfileUpdateFail() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(validPassword);
		when(request.getParameter("passwordConfirm")).thenReturn(validPassword);
		when(request.getParameter("name")).thenReturn(validName);
		when(request.getParameter("surname")).thenReturn(validSurname);
		when(personService.find(person.getId())).thenReturn(person);
		when(personService.find(person.getPhone())).thenReturn(person);
		when(personService.update(person)).thenReturn(false);
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=profile_update", true), result);	
	}
	
	@Test
	public void testProfileUpdateFailPhoneExist() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(validPassword);
		when(request.getParameter("passwordConfirm")).thenReturn(validPassword);
		when(request.getParameter("name")).thenReturn(validName);
		when(request.getParameter("surname")).thenReturn(validSurname);
		when(personService.find(person.getId())).thenReturn(person);
		when(personService.find(person.getPhone())).thenReturn(new Person());
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=phone_exist", true), result);	
	}
	
	@Test
	public void testProfileUpdateFailPhoneFormat() throws IOException, ServletException {
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=phone_format", true), result);	
	}
	
	@Test
	public void testProfileUpdateFailPasswordFormat() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(invalidPassword);
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=password", true), result);	
	}
	
	@Test
	public void testProfileUpdateFailPasswordConfirm() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(validPassword);
		when(request.getParameter("passwordConfirm")).thenReturn(invalidPassword);
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=password_confirm", true), result);	
	}
	
	@Test
	public void testProfileUpdateFailNameFormat() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(validPassword);
		when(request.getParameter("passwordConfirm")).thenReturn(validPassword);
		when(request.getParameter("name")).thenReturn(invalidName);
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=name", true), result);	
	}
	
	@Test
	public void testProfileUpdateFailSurnameFormat() throws IOException, ServletException {
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(validPassword);
		when(request.getParameter("passwordConfirm")).thenReturn(validPassword);
		when(request.getParameter("name")).thenReturn(validName);
		when(request.getParameter("surname")).thenReturn(invalidSurname);
		
		Page result = profileUpdateCommand.execute(request, response);
		
		verify(request, times(1)).getSession(false);
		verify(session, never()).setAttribute(anyString(), anyString());
		
		assertEquals(new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=surname", true), result);	
	}

}
