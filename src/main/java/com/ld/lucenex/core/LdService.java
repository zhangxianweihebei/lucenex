/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: LdService.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.core
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年11月27日 上午10:40:08
 * @version: V1.0
 */
package com.ld.lucenex.core;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Constants;
import com.ld.lucenex.interce.LdInterface;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @ClassName: LdService
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年11月27日 上午10:40:08
 */
public class LdService implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LdService.class);


    public static final Constants constants = BaseConfig.baseConfig();

    /**
     * 获取一个默认的Service 默认就是第一个库
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            LdService interceptor = new LdService();
            Enhancer e = new Enhancer();
            e.setSuperclass(clazz);
            e.setCallback(interceptor);
            Object bean = e.create();
            return (T) bean;
        } catch (Throwable e) {
            logger.error("LdService->newInstance error", e);
        }
        return null;
    }

    /**
     * 获取一个指定库的Service
     *
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz, String key) {
        try {
            ManySource.setKey(key);
            return newInstance(clazz);
        } catch (Throwable e) {
            logger.error("LdService->newInstance error", e);
        }
        return null;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        List<LdInterface> ldInterface = constants.getLdInterface();
        for (LdInterface inter : ldInterface) {
            boolean beforeMethod = inter.beforeMethod(obj, method, args);
            if (!beforeMethod) break;
        }
        Object retValFromSuper = null;
        try {
            if (!Modifier.isAbstract(method.getModifiers())) {
                retValFromSuper = proxy.invokeSuper(obj, args);
            }
        } finally {
            for (LdInterface inter : ldInterface) {
                boolean beforeMethod = inter.afterMethod(retValFromSuper, method, args);
                if (!beforeMethod) break;
            }
        }
        return retValFromSuper;
    }
}
