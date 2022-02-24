package com.example.taxiservice.web.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Context listener
 */
public class ContexListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(ContexListener.class);

    public void contextDestroyed(ServletContextEvent event)  { 
    	LOG.debug("Servlet context destruction starts");
		// do nothing
    	LOG.debug("Servlet context destruction finished");
        
    }

    public void contextInitialized(ServletContextEvent event)  { 
    	LOG.debug("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initCommandContainer();
		initI18N(servletContext);
	
		LOG.debug("Servlet context initialization finished");
		
    }
    
    /**
	 * Initializes i18n subsystem.
	 */
	private void initI18N(ServletContext servletContext) {
		LOG.debug("I18N subsystem initialization started");
		
		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			LOG.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}							
			
			LOG.debug("Application attribute set: locales --> " + locales);
			servletContext.setAttribute("locales", locales);
			
		}
		
		String defaultLocale = servletContext.getInitParameter(Config.FMT_LOCALE);
		if (defaultLocale == null || defaultLocale.isEmpty()) {
			LOG.warn("'defaultLocale' init parameter is empty, the (preferable) browser's locale will be used");
		}else {
			LOG.debug("Application attribute set: defaultLocale --> " + defaultLocale);
			servletContext.setAttribute("defaultLocale", defaultLocale);
		}
		
		LOG.debug("I18N subsystem initialization finished");
		
	}
	
	/**
	 * Initializes CommandContainer.
	 */
	private void initCommandContainer() {
		LOG.debug("Command container initialization started");
		
		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("com.example.taxiservice.web.command.CommandContainer");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
				
		LOG.debug("Command container initialization finished");
	}

	
}
