package com.example.taxiservice.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.taxiservice.web.Controller;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;
import com.example.taxiservice.web.command.common.HomePageCommand;
import com.example.taxiservice.web.command.trip.NewTripCommand;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Spy
    private HashMap<String, Command> commands;

    @Spy
    private HomePageCommand homePageCommand;

    @Spy
    private NewTripCommand newTripCommand;

    @InjectMocks
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        commands.put("home_page", homePageCommand);
        commands.put("new_trip", newTripCommand);
        when(request.getRequestDispatcher(Path.PAGE__HOME_PAGE)).thenReturn(dispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getParameter(Parameter.COMMAND)).thenReturn("home_page");

        controller.doGet(request, response);

        verify(response, never()).sendRedirect(anyString());
        verify(request, times(1)).getRequestDispatcher(Path.PAGE__HOME_PAGE);
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetNoCommand() throws ServletException, IOException {
        when(request.getParameter(Parameter.COMMAND)).thenReturn(" ");

        controller.doGet(request, response);

        verify(response, never()).sendRedirect(anyString());
        verify(request, times(1)).getRequestDispatcher(Path.PAGE__HOME_PAGE);
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter(Parameter.COMMAND)).thenReturn("new_trip");
        when(request.getMethod()).thenReturn("POST");

        controller.doPost(request, response);

        verify(response, times(1)).sendRedirect(anyString());
        verify(request, never()).getRequestDispatcher(anyString());
        verify(dispatcher, never()).forward(request, response);
    }
}
