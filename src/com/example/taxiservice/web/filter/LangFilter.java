package com.example.taxiservice.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
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
	
	public void destroy() {
		LOG.debug("Filter destruction starts");
		// do nothing
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		LOG.debug("Filter starts");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		ServletContext servletContext = httpRequest.getServletContext();
		
		String locale = request.getParameter("locale");
		String locales = servletContext.getInitParameter("locales");
		
		if(locale != null && locales.contains(locale)) {
			LOG.debug("Request attribute: locale = " + locale);
			HttpSession session = httpRequest.getSession();
			Config.set(session, Config.FMT_LOCALE, locale);
			session.setAttribute("locale", locale);
			LOG.debug("Set the session attribute: locale --> " + locale);
		}
		
		LOG.debug("Filter finished");
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		// do nothing
		LOG.debug("Filter initialization finished");		
	}

}
