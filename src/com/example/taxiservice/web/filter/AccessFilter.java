package com.example.taxiservice.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Path;

/**
 * Security filter
 */
public class AccessFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccessFilter.class);
	
	// commands access
//	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> user = new ArrayList<String>();	
	private static List<String> guest = new ArrayList<String>();
	private static List<String> common = new ArrayList<String>();


	public void destroy() {
		LOG.debug("Filter destruction starts");
		// do nothing
		LOG.debug("Filter destruction finished");
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		
		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "error_jsp.anchor.access_denied";
			
			request.setAttribute("errorMessage", errorMessasge);
			LOG.debug("Set the request attribute: errorMessage --> " + errorMessasge);
			request.setAttribute("errorMessasge", errorMessasge);
			request.getRequestDispatcher(Path.PAGE__ERROR).forward(request, response);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
		boolean result = false;
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		Object personID = session.getAttribute("personId");

		String commandName = request.getParameter("command");
		LOG.debug(commandName);
		
		if (commandName != null && !commandName.isEmpty()) {
			if(personID == null && guest.contains(commandName)) {
				result = true;
			}else if(personID != null && user.contains(commandName)) {
				result = true;
			}else if(common.contains(commandName)) {
				result = true;
			}
		}else {
			result = true;
		}
		
		return result;
	}


	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		
		// user
		user = asList(fConfig.getInitParameter("user"));
		LOG.debug("User commands --> " + user);
		
		// guest
		guest = asList(fConfig.getInitParameter("guest"));
		LOG.debug("Guest commands --> " + guest);
		
		// common
		common = asList(fConfig.getInitParameter("common"));
		LOG.debug("Common commands --> " + common);
		
		LOG.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string
	 * 
	 * @param str - parameter values string
	 * 
	 * @return list of parameter values
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) list.add(st.nextToken());
		return list;		
	}


}
