/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: LdInterface.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年11月27日 下午2:14:13
 * @version: V1.0  
 */
package com.ld.lucenex.interce;

import java.lang.reflect.Method;

/**
 * 拦截器
 * @ClassName: LdInterface
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年11月27日 下午2:14:13
 */
public interface LdInterface {
	
	/**
	 * 前置通知
	 * @param args 
	 * @param method 
	 * @param obj 
	 * @Title: beforeMethod
	 * @Description: TODO
	 * @return
	 * @return: boolean
	 */
	default boolean beforeMethod(Object obj, Method method, Object[] args) {return true;}
	/**
	 * 后置通知
	 * @param args 
	 * @param method 
	 * @param retValFromSuper 
	 * @Title: afterMethod
	 * @Description: TODO
	 * @return
	 * @return: boolean
	 */
	default boolean afterMethod(Object retValFromSuper, Method method, Object[] args) {return true;}

}
