package com.example.taxiservice.web.command.person;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
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

@RunWith(MockitoJUnitRunner.class)
public class SignInCommandTest {
	
	@Mock 
	private HttpServletRequest request;
	@Mock 
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@Mock
	private PersonService personService;
	
	@InjectMocks
	private SignInCommand signInCommand;
	
	private Person person;
	private String phone = "0123456781";
	private String password = "Client#1";

	@Before
	public void setUp() throws Exception {
		person = new Person();
		person.setId(4L);
		person.setPhone("0123456781");
		person.setPassword("7b9c03298c85e20c45d2e3128e0c28a0a2b72c421277335ede1d3ad688692be6");
		person.setRoleId(1);
		
		when(request.getParameter("phone")).thenReturn(phone);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getMethod()).thenReturn("POST");
		when(request.getSession(true)).thenReturn(session);
	}

	@Test
	public void testSignIn() throws IOException, ServletException {
		when(personService.find(phone)).thenReturn(person);
		
		signInCommand.execute(request, response);
		
		verify(request, times(1)).getSession(true);
		verify(session, times(5)).setAttribute(anyString(), any());
	}
	
	@Test
	public void testSignInFail() throws IOException, ServletException {
		signInCommand.execute(request, response);
		
		verify(request, never()).getSession(true);
		verify(session, never()).setAttribute(anyString(), any());
	}


}
