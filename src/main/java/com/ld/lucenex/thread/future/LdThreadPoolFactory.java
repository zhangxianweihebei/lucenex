package com.ld.lucenex.thread.future;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * 线程池工程   处理所有的线程
 * @author Administrator
 */
public class LdThreadPoolFactory extends ThreadPoolExecutor{
	
	/***********************************           默认配置                **************************/
		
	//最小线程数
	static int corePoolSize = 5;
	//最大线程数
	static int maximumPoolSize = 10;
	//空闲时间
	static int keepAliveTime = 0;
	//单位秒
	static TimeUnit unit = TimeUnit.MILLISECONDS;
	//处理队列
	static BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
	//线程创建工厂
	static ThreadFactory threadFactory = Executors.defaultThreadFactory();
	//线程handler
	static RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
	
	/***********************************           默认配置                **************************/
	
	DelayQueue<OvertimeFuture<?>> timeLimitJobQueue = new DelayQueue<OvertimeFuture<?>>();
	

	public LdThreadPoolFactory(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
		initMonitor();
	}
	
	public LdThreadPoolFactory() {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}
	
	
	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		// TODO 自动生成的方法存根
		return new LdFuture<T>(callable);
	}
	@Override
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
		// TODO 自动生成的方法存根
		return new LdFuture<T>(runnable, value);
	}
	
	
	@Override
	public <T> LdFuture<T> submit(Callable<T> task) {
		return (LdFuture<T>) super.submit(task);
	}
	
	@Override
	public LdFuture<?> submit(Runnable task) {
		return (LdFuture<?>) super.submit(task);
	}
	
	@Override
	public <T> LdFuture<T> submit(Runnable task, T result) {
		return (LdFuture<T>) super.submit(task, result);
	}
	
	@Override
	public void execute(Runnable command) {
		super.execute(command);
	}
	
	
	/********** 自定义************/
	/**
	 * 线程执行 带 超时时间
	 * @param runnable
	 * @param limitTime
	 * @param timeUnit
	 */
	public void execute(Runnable runnable,long limitTime,TimeUnit timeUnit) {
		LdFuture<?> ldFuture = submit(runnable);
		timeLimitJobQueue.put(new OvertimeFuture<>(ldFuture, limitTime, timeUnit));
	}
	public <T> LdFuture<T> submit(Runnable task, T result,long limitTime,TimeUnit timeUnit) {
		LdFuture<T> ldFuture = submit(task, result);
		timeLimitJobQueue.put(new OvertimeFuture<>(ldFuture, limitTime, timeUnit));
		return ldFuture;
	}
	public LdFuture<?> submit(Runnable task,long limitTime,TimeUnit timeUnit) {
		LdFuture<?> ldFuture = submit(task);
		timeLimitJobQueue.put(new OvertimeFuture<>(ldFuture, limitTime, timeUnit));
		return ldFuture;
	}
	public <T> LdFuture<T> submit(Callable<T> task,long limitTime,TimeUnit timeUnit) {
		LdFuture<T> ldFuture = submit(task);
		timeLimitJobQueue.put(new OvertimeFuture<>(ldFuture, limitTime, timeUnit));
		return ldFuture;
	}
	
	/**
	 *超时监控
	 */
	public void initMonitor() {
		submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				while (!Thread.interrupted()) {//判断当前线程是否中断
					OvertimeFuture<?> timeLimitFuture = null;
					try {
						timeLimitFuture = timeLimitJobQueue.take();
						if(timeLimitFuture != null) {
							
							timeLimitFuture.future.get(1, TimeUnit.MILLISECONDS);
						}
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						timeLimitFuture.future.cancel(true);
					}
				}
				return null;
			}
		}).addListener(new LdFutureListener<String>() {
			
			@Override
			public void success(String v) {
			}
			
			@Override
			public void error(Throwable throwable) {
				System.out.println("关闭");
			}
		});;
	}

}
