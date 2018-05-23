package com.ld.lucenex.thread.future;



public interface LdFutureListener<V> {
	void success(V v);
	void error(Throwable throwable);
}
