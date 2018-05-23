package com.ld.lucenex.thread;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.ld.lucenex.thread.future.LdThreadPoolFactory;

/**
 * 单利模式创建线程池
 * @author Administrator
 *
 */
public class LdThreadPool {

	private	static LdThreadPool kit =new LdThreadPool();
	private LdThreadPoolFactory poolFactory;
	public static LdThreadPool build() {
		synchronized (kit) {
			if(kit.poolFactory == null) {
				kit.poolFactory = new LdThreadPoolFactory();
			}
			return kit;
		}
	}
	public static LdThreadPool build(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		synchronized (kit) {
			if(kit.poolFactory == null) {
				kit.poolFactory = new LdThreadPoolFactory(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
			}
			return kit;
		}
	}
	
	public LdThreadPoolFactory get() {
		return get(false);
	}
	public LdThreadPoolFactory get(boolean flag) {
		if(flag) {
			poolFactory = new LdThreadPoolFactory();
		}
		poolFactory.initMonitor();
		return poolFactory;
	}
	
	public void close() {
		poolFactory.shutdown();
	}
	public void closeNew() {
		poolFactory.shutdownNow();
	}
}
