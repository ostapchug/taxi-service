package com.example.taxiservice.factory.config;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaCofig - our configuration implementation.
 */
public class JavaCofig implements Config {

	private static final Logger LOG = LoggerFactory.getLogger(JavaCofig.class);

	private Reflections scanner;
	private Map<Class<?>, Class<?>> ifc2ImplClass;

	public JavaCofig(String packageToScan, Map<Class<?>, Class<?>> ifc2ImplClass) {
		this.ifc2ImplClass = ifc2ImplClass;
		this.scanner = new Reflections(packageToScan);
		LOG.info("JavaCofig initialized");
	}

	@Override
	public <T> Class<? extends T> getImplClass(Class<T> ifc) {

		// if implementations more than one then required implementation needs to be
		// specified in the map
		return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {

			Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

			if (classes.size() != 1) {
				LOG.error(ifc.getSimpleName() + " has 0 or more than one impl");
				throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
			}

			return classes.iterator().next();
		}).asSubclass(ifc);
	}

	@Override
	public Reflections getScanner() {
		return scanner;
	}
}
