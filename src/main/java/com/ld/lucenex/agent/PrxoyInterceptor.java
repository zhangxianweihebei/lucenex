package com.ld.lucenex.agent;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public abstract  class PrxoyInterceptor implements MethodInterceptor{
	
	protected abstract boolean BeforeExecution(Object proxy, Method method, Object[] args);
	protected abstract Object AfterExecution(Object retVal, Method method, Object[] args);

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if(BeforeExecution(obj, method, args)) {
			Object retVal = proxy.invokeSuper(obj, args);
			retVal = AfterExecution(retVal, method, args);
			return retVal;
		}else {
			return null;
		}
	}
}
