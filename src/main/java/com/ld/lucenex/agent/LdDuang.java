/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: LdDuang.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.agent
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:52:50
 * @version: V1.0  
 */
package com.ld.lucenex.agent;

import com.ld.lucenex.core.Service;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @ClassName: LdDuang
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:52:50
 */
public class LdDuang {
	/**
	 * @Title: duang
	 * @Description: TODO
	 * @return: void
	 */
	public static <T> T duang(Object object) {
		return duang(object.getClass());
	}
	@SuppressWarnings("unchecked")
	public static <T> T duang(Class<?> clas) {
		Callback callback = LdPrxoyListenin.build().getListenin(clas.getName());
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clas);
		enhancer.setCallback(callback != null ?callback:NoOp.INSTANCE);
		return (T) enhancer.create();
	}

}
