package com.ld.lucenex.agent;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class LdProxyFactory {

	/**
	 * 简单代理
	 * @param <T>
	 * @param clas
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createObject(Class<?> clas) {
		Callback callback = LdPrxoyListenin.build().getListenin(clas.getName());
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clas);
		enhancer.setCallback(callback != null ?callback:NoOp.INSTANCE);
		return (T) enhancer.create();
	}
}
