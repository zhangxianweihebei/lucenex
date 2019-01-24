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

import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.core.ManySource;
import com.ld.lucenex.interce.LdInterface;

/**
 * @ClassName: NRTIterface
 * @Description: 近实时转换
 * @author: Myzhang
 * @date: 2018年11月27日 下午2:35:56
 */
public class NRTIterface implements LdInterface {

	private static Logger logger = LoggerFactory.getLogger(NRTIterface.class);

	/**
	 * 在方法执行后异步运行
	 *
	 * @param retValFromSuper
	 * @param method
	 * @param args
	 * @return
	 */
	@Override
	public boolean afterMethod(Object retValFromSuper, Method method, Object[] args) {
		String methodName = method.getName();
		if (methodName.indexOf("add") != -1 || methodName.indexOf("save") != -1
				|| methodName.indexOf("update") != -1 || methodName.indexOf("del") != -1) {
			SourceConfig dataSource = ManySource.getDataSource();
			try {
				dataSource.restartReader();
				if (BaseConfig.baseConfig().isDevMode()) {
					dataSource.getWriter().commit();
				}
				logger.info("NRTIterface->afterMethod success");
			} catch (IOException e) {
				logger.error("NRTIterface->afterMethod error", e);
			}
		}
		return true;
	}

}
