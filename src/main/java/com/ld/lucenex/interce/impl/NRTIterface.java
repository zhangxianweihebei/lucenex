/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: NRTIterface.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.interce.impl
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年11月27日 下午2:35:56
 * @version: V1.0
 */
package com.ld.lucenex.interce.impl;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.core.ManySource;
import com.ld.lucenex.interce.LdInterface;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: NRTIterface
 * @Description: 近实时转换
 * @author: Myzhang
 * @date: 2018年11月27日 下午2:35:56
 */
public class NRTIterface implements LdInterface {
    @Override
    public boolean afterMethod(Object retValFromSuper, Method method, Object[] args) {
        String methodName = method.getName();
        if (methodName.indexOf("add") != -1 || methodName.indexOf("save") != -1
                || methodName.indexOf("update") != -1 || methodName.indexOf("del") != -1) {
            SourceConfig dataSource = ManySource.getDataSource();
            CompletableFuture.runAsync(() -> {
                try {
                    dataSource.restartReader();
                    if (BaseConfig.baseConfig().isDevMode()) {
                        dataSource.getWriter().commit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return true;
    }

}
