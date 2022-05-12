package com.example.taxiservice.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.factory.config.Config;

/**
 * AppContext - contains object factory, cache of already created objects and
 * other required resources.
 */
public class AppContext {

	private static final Logger LOG = LoggerFactory.getLogger(AppContext.class);

	private ObjectFactory factory;
	private Config config;
	private DataSource dataSource;
	private Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

	public AppContext(Config config, DataSource dataSource) {
		this.config = config;
		this.dataSource = dataSource;
		LOG.info("AppContext initialized");
	}

	public Config getConfig() {
		return config;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public ObjectFactory getFactory() {
		return factory;
	}

	public void setFactory(ObjectFactory factory) {
		this.factory = factory;
	}

	public <T> T getObject(Class<T> type) {
		if (cache.containsKey(type)) {
			return type.cast(cache.get(type));
		}

		Class<? extends T> implClass = type;

		// if class is interface, get the implementation
		if (type.isInterface()) {
			implClass = config.getImplClass(type);
		}

		// create object
		T t = factory.createObject(implClass);

		if (implClass.isAnnotationPresent(Singleton.class)) {
			cache.put(type, t);
		}

		return t;
	}

}
