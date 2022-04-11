package com.example.taxiservice.factory.config;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.AppContext;
import com.example.taxiservice.factory.annotation.InjectByType;
/**
 * InjectByTypeAnnotationObjectConfigurator - injects objects in suitably annotated fields by specified field type.
 */
public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
	
	private static final Logger LOG = LoggerFactory.getLogger(InjectByTypeAnnotationObjectConfigurator.class);
	
	@Override
	public void configure(Object t, AppContext context) {
		
		LOG.debug("Configuring starts");
		
		for (Field field : t.getClass().getDeclaredFields()) {
			
			if (field.isAnnotationPresent(InjectByType.class)) {
				Object object = context.getObject(field.getType());
			    field.setAccessible(true);
			    try {
					field.set(t, object);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOG.error(e.getMessage());
				}   
			}
		    
		}
		
		LOG.debug("Configuring finished");
	}

}
