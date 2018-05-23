package com.ld.lucenex.thread.future;



import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.FutureTask;

public class LdFuture<V> extends FutureTask<V>{
	
	private Set<LdFutureListener<V>> listeners;
	private Object result;
	private boolean flag;
	
	public LdFuture(Callable<V> callable) {
		super(callable);
		listeners = new CopyOnWriteArraySet<>();
	}
	
	public LdFuture(Runnable runnable, V result) {
        super(runnable, result);
        listeners=  new CopyOnWriteArraySet<>();
    }
	
	public void addListener(LdFutureListener<V> ldFutureListener) {
		if(flag) {
			notifyListeners(ldFutureListener);
		}else {
			listeners.add(ldFutureListener);
		}
	}
	
	@Override
	protected void set(V v) {
		super.set(v);
		result = v;
		flag = true;
		notifyListeners();
	}
	
	@Override
	protected void setException(Throwable t) {
		super.setException(t);
		result = t;
		flag = true;
		notifyListeners();
	}
	
	private void notifyListeners() {
		for (LdFutureListener<V> ldFutureListener : listeners) {
			notifyListeners(ldFutureListener);
		}
	}
	@SuppressWarnings("unchecked")
	void notifyListeners(LdFutureListener<V> ldFutureListener) {
		if(result  instanceof Throwable) {//异常
			ldFutureListener.error((Throwable) result);
		}else {
			ldFutureListener.success((V) result);
		}
		ldFutureListener=null;
	}
}
