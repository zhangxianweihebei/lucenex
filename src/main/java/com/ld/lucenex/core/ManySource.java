package com.ld.lucenex.core;

import java.util.concurrent.ConcurrentHashMap;

import com.ld.lucenex.config.SourceConfig;

public class ManySource {
	
	private static volatile ConcurrentHashMap<String, SourceConfig> dataSource = new ConcurrentHashMap<>();
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	/**
	 * @Title:ManySource
	 * @Description:TODO
	 */
	private ManySource() {
		// TODO 自动生成的构造函数存根
	}
	
	public static void putDataSource(String k,SourceConfig v) {
		dataSource.put(k, v);
	}
	public static SourceConfig getDataSource(String k) {
		return dataSource.get(k);
	}
	public static SourceConfig getDataSource() {
		return dataSource.get(getContextHolder());
	}
	
	public static void putContextHolder(String k) {
		contextHolder.set(k);
	}
	public static String getContextHolder() {
		return contextHolder.get();
	}
	public static void clearContextHolder() {
		contextHolder.remove();
	}
	
}
