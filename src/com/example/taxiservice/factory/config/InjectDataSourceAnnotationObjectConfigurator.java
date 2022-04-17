package com.example.taxiservice.factory.config;

import java.lang.reflect.Field;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.AppContext;
import com.example.taxiservice.factory.annotation.InjectDataSource;

/**
 * InjectDataSourceAnnotationObjectConfigurator - injects DataSource in suitably annotated field.
 */
public class InjectDataSourceAnnotationObjectConfigurator implements ObjectConfigurator {
	
	private static final Logger LOG = LoggerFactory.getLogger(InjectDataSourceAnnotationObjectConfigurator.class);
	
	@Override
	public void configure(Object t, AppContext context) {
		
		LOG.debug("Configuring starts");
		
		Class<?> current = t.getClass();
		while(current.getSuperclass() != null){
			
			for (Field field : current.getDeclaredFields()) {
				
				if (field.isAnnotationPresent(InjectDataSource.class)) {
					DataSource dataSource = context.getDataSource();
					field.setAccessible(true);
					try {
						field.set(t, dataSource);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						LOG.error(e.getMessage());
					}
				}
				
			}
			
		    current = current.getSuperclass();
		}		
		
		LOG.debug("Configuring finished");		
	}

}
