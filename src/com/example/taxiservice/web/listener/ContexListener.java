package com.example.taxiservice.web.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.jstl.core.Config;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.AppContext;
import com.example.taxiservice.factory.Application;
import com.example.taxiservice.web.command.Command;

/**
 * Context listener
 */
public class ContexListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(ContexListener.class);
	
	@Resource(name="jdbc/taxi-service-db")
	private DataSource dataSource;

    public void contextDestroyed(ServletContextEvent event)  { 
    	LOG.debug("Servlet context destruction starts");
		// do nothing
    	LOG.debug("Servlet context destruction finished");
        
    }

    public void contextInitialized(ServletContextEvent event)  { 
    	LOG.debug("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initCommandContainer(servletContext);
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
	private void initCommandContainer(ServletContext servletContext) {
		LOG.debug("Command container initialization started");
		
		AppContext context = Application.run("com.example.taxiservice",  new HashMap<Class<?>, Class<?>>(), dataSource);
		String commandsValue = servletContext.getInitParameter("commands");
		
		// initialize commands container		
		if (commandsValue == null || commandsValue.isEmpty()) {
			LOG.error("'commands' init parameter is empty, please update your 'web.xml' file");
		} else {
			Map<String, Command> commands = new HashMap<>();
			StringTokenizer st = new StringTokenizer(commandsValue);
			
			while (st.hasMoreTokens()) {
				String [] commandNameClassPairs = st.nextToken().split("=");
				String commandName = commandNameClassPairs[0];
				
				try {
					Class<?> commandClass = Class.forName(commandNameClassPairs[1]);
					Command command = (Command) context.getObject(commandClass);
					commands.put(commandName, command);
				} catch (ClassNotFoundException e) {
					LOG.error(e.getMessage());
				}
			}							
			
			LOG.debug("Command container was successfully initialized");
			LOG.debug("Number of commands --> " + commands.size());
			servletContext.setAttribute("commands", commands);
		}		
				
		LOG.debug("Command container initialization finished");
	}

	
}
