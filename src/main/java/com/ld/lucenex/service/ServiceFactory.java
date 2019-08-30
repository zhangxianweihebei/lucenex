package com.ld.lucenex.service;

import com.ld.lucenex.base.Const;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {

    private static Map<String, Service> serviceMap = new HashMap<>();

    public static <T> T getService(Class<T> clazz){
        return getService(clazz, Const.DEFAULT_SERVICE_KEY);
    }
    public static <T> T getService(Class<T> clazz,String key){
        String typeName = clazz.getTypeName();
        String service_key = String.join("_", typeName, key);
        Service service = serviceMap.get(service_key);
        if (service != null){
            return (T) service;
        }
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        T t = null;
        if (constructor != null){
            try {
                t = constructor.newInstance(key);
                serviceMap.put(service_key, (Service) t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
