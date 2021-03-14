package com.ld.lucenex.interceptor;

import java.lang.reflect.Method;

import com.ld.lucenex.core.LuceneX;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.lucene.index.IndexWriter;

public class IndexWriterInterceptor implements MethodInterceptor{

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object invoke = proxy.invokeSuper(obj, args);
		String name = method.getName();
		if (name.startsWith("add") || name.startsWith("update") || name.startsWith("del")) {
			if (obj instanceof IndexWriter){
				LuceneX.getInstance().syncIndexSource((IndexWriter)obj);
			}
		}
		return invoke;
	}

	
}
