package com.example.taxiservice.factory;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.config.JavaCofig;

/**
 * Application - initializes resources and returns context.
 */
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    
    public static AppContext run(String packageToScan, Map<Class<?>, Class<?>> ifc2ImplClass, DataSource dataSource) {
        LOG.debug("Method run starts");
        JavaCofig config = new JavaCofig(packageToScan, ifc2ImplClass);
        AppContext context = new AppContext(config, dataSource);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        LOG.debug("Method run finished");
        return context;
    }
}
