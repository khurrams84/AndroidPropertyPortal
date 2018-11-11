package com.mhaseeb.property.ui.common.service;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammadmoiz on 11/10/18.
 */

public class ServiceLocator {

    public static Map<String, Object> cache;

    public static void initServiceLocator(Context context) {

        cache = new HashMap<>();
        cache.put(ApplicationContext.class.getName(), new ApplicationContext(context));

    }

    public static <T> T getService(Class<T> clazz) {

        T service = null;

        try {
            if (cache.get(clazz.getName()) == null) {
                cache.put(clazz.getName(), Class.forName(clazz.getName()).getDeclaredConstructor().newInstance());
            }
            return (T) cache.get(clazz.getName());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return service;
    }

}
