package com.example.taxiservice.web.command.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.taxiservice.model.Person;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Error;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Parameter;
import com.example.taxiservice.web.Path;

@RunWith(MockitoJUnitRunner.class)
public class SignUpCommandTest {

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private PersonService personService;

	@InjectMocks
	private SignUpCommand signUpCommand;

	private Person person;
	private String phone = "0123456781";
	private String password = "Client#1";
	private String validName = "Tom", invalidName = "tom";
	private String validSurname = "Smith", invalidSurname = "smith";

	@Before
	public void setUp() throws Exception {
		person = new Person();
		person.setPhone("0123456781");
		person.setPassword("7b9c03298c85e20c45d2e3128e0c28a0a2b72c421277335ede1d3ad688692be6");
		person.setName(validName);
		person.setSurname(validSurname);
		person.setRoleId(1);

		when(request.getMethod()).thenReturn("POST");
	}

	@Test
	public void testSignUp() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);
		when(request.getParameter(Parameter.PERSON__PASSWORD)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__NAME)).thenReturn(validName);
		when(request.getParameter(Parameter.PERSON__SURNAME)).thenReturn(validSurname);
		when(personService.insert(person)).thenReturn(true);

		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_IN_PAGE, true), result);
	}

	@Test
	public void testSignUpFail() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);
		when(request.getParameter(Parameter.PERSON__PASSWORD)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__NAME)).thenReturn(validName);
		when(request.getParameter(Parameter.PERSON__SURNAME)).thenReturn(validSurname);
		when(personService.insert(person)).thenReturn(false);

		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.PROFILE__CREATE, true),
				result);
	}

	@Test
	public void testSignUpFailPhoneExist() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);
		when(request.getParameter(Parameter.PERSON__PASSWORD)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__NAME)).thenReturn(validName);
		when(request.getParameter(Parameter.PERSON__SURNAME)).thenReturn(validSurname);

		when(personService.find(phone)).thenReturn(person);
		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.PHONE__EXIST, true), result);
	}

	@Test
	public void testSignUpFailPhoneFormat() throws IOException, ServletException {
		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.PHONE__FORMAT, true), result);
	}

	@Test
	public void testSignUpFailPasswordFormat() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);

		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.PASSWORD__FORMAT, true),
				result);
	}

	@Test
	public void testSignUpFailPasswordConfirm() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);
		when(request.getParameter(Parameter.PERSON__PASSWORD)).thenReturn(password);

		Page result = signUpCommand.execute(request, response);
		assertEquals(
				new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.PASSWORD__CONFIRM_WRONG, true),
				result);
	}

	@Test
	public void testSignUpFailNameFormat() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);
		when(request.getParameter(Parameter.PERSON__PASSWORD)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__NAME)).thenReturn(invalidName);

		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.NAME__FORMAT, true), result);
	}

	@Test
	public void testSignUpFailSurnameFormat() throws IOException, ServletException {
		when(request.getParameter(Parameter.PERSON__PHONE)).thenReturn(phone);
		when(request.getParameter(Parameter.PERSON__PASSWORD)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM)).thenReturn(password);
		when(request.getParameter(Parameter.PERSON__NAME)).thenReturn(validName);
		when(request.getParameter(Parameter.PERSON__SURNAME)).thenReturn(invalidSurname);

		Page result = signUpCommand.execute(request, response);
		assertEquals(new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.SURNAME__FORMAT, true),
				result);
	}

}
