package com.example.taxiservice.factory;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.config.ObjectConfigurator;

/**
 * Object factory - creates and configures objects.
 */
public class ObjectFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectFactory.class);
    private final AppContext context;
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(AppContext context) {
        this.context = context;
        // finds all available configurators.
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner()
                .getSubTypesOf(ObjectConfigurator.class)) {
            try {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.info("ObjectFactory initialized");
    }

    public <T> T createObject(Class<T> implClass) {
        T t = create(implClass);
        configure(t);
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }

    private <T> T create(Class<T> implClass) {
        T t = null;

        try {
            t = implClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return t;
    }
}
