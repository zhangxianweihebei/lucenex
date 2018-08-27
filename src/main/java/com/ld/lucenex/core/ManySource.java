package com.ld.lucenex.core;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ld.lucenex.config.SourceConfig;

public class ManySource{
	
	private static Logger logger = LoggerFactory.getLogger(ManySource.class);
	
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
		return k == null ? null :(dataSource == null ? null : dataSource.get(k));
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
	
	/**
	 * @Title: submit
	 * @Description: 提交所有源
	 * @return: void
	 */
	public static void submit() {
		dataSource.forEach((k,v)->{
			logger.info("提交<"+k+">数据源");
			try {
				v.getWriter().commit();
				v.restartReader();
			} catch (Exception e) {
				logger.error("提交<"+k+">数据源",e);
			}
		});
	}
	
	/**
	 * @Title: close
	 * @Description: 关闭所有源
	 * @return: void
	 */
	public static void close() {
		submit();
		dataSource.forEach((k,v)->{
			try {
				v.getWriter().close();
			} catch (IOException e) {
			}
		});
	}
	
}
