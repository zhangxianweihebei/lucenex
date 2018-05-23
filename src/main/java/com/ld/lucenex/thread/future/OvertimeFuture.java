package com.ld.lucenex.thread.future;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


/**
 * 超时管理
 * @author Administrator
 * @param <V>
 *
 */
public class OvertimeFuture<V> implements Delayed{
	public LdFuture<V> future;
	long limitTime;  
	long triggerTime;

	public OvertimeFuture(LdFuture<V> future,long limitTime,TimeUnit unit) {
		this.future = future;
		this.limitTime = limitTime;
		this.triggerTime = TimeUnit.NANOSECONDS.convert(limitTime, unit) + System.nanoTime();
	}

	@Override
	public int compareTo(Delayed o) {
		if (o == null || !(o instanceof OvertimeFuture))  
			return 1;  
		if (o == this)  
			return 0;  
		OvertimeFuture<?> future = (OvertimeFuture<?>) o;  
		if (this.limitTime > future.limitTime) {  
			return 1;  
		} else if (this.limitTime == future.limitTime) {  
			return 0;  
		} else {  
			return -1;  
		}  
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(triggerTime - System.nanoTime(), TimeUnit.NANOSECONDS);  
	}

}
