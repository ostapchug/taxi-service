package com.example.taxiservice.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Language filter
 */ 
public class LangFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(LangFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		LOG.debug("Filter starts");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String locale = request.getParameter("locale");
		
		if(locale != null) {
			HttpSession session = httpRequest.getSession();
			Config.set(session, Config.FMT_LOCALE, locale);
			session.setAttribute("locale", locale);
		}
		
		LOG.debug("Filter finished");
		chain.doFilter(request, response);
	}

}
