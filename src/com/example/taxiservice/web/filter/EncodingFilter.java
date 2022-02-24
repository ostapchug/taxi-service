package com.example.taxiservice.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encoding filter
 */
public class EncodingFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(EncodingFilter.class);
	private String encoding;

	public void destroy() {
		LOG.debug("Filter destruction starts");
		// do nothing
		LOG.debug("Filter destruction finished");

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		LOG.debug("Request uri --> " + httpRequest.getRequestURI());
		
		String requestEncoding = request.getCharacterEncoding();
		if (requestEncoding == null) {
			LOG.debug("Request encoding = null, set encoding --> " + encoding);
			request.setCharacterEncoding(encoding);
		}
		
		LOG.debug("Filter finished");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		
		encoding = fConfig.getInitParameter("encoding");
		
		LOG.debug("Encoding from init params --> " + encoding);
		LOG.debug("Filter initialization finished");

		
	}

}
