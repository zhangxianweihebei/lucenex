package com.ld.lucenex.service.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ServiceFactory {

    public static <T> T getService(Class<T> clazz){
        try {
            T t = clazz.newInstance();
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> T getService(Class<T> clazz,String key){
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
