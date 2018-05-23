package com.ld.lucenex.thread;



import java.util.logging.Logger;

/**
 * 日志工厂 内置 JDK 实现
 * @author Administrator
 *
 */
public class LoggerFactory{

	public static Logger getLogger(Class<?> c) {
		return Logger.getLogger(c.getName());
	}

}
