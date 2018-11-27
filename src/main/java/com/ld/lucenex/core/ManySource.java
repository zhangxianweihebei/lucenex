package com.ld.lucenex.core;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ld.lucenex.config.SourceConfig;

public class ManySource{
	
	private static Logger logger = LoggerFactory.getLogger(ManySource.class);
	
	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	private static volatile ConcurrentHashMap<String, SourceConfig> dataSource = new ConcurrentHashMap<>();
	
	public static void putDataSource(String k,SourceConfig v) {
		dataSource.put(k, v);
	}
	
	public static void setKey(String key) {
		threadLocal.set(key);
	}
	
	public static SourceConfig getDataSource(String k) {
		return dataSource.get(k);
	}
	public static SourceConfig getDataSource() {
		SourceConfig sourceConfig = null;
		String key = threadLocal.get();
		if(StringUtils.isNotBlank(key)) {
			sourceConfig = dataSource.get(key);
		}else {
			Set<Entry<String, SourceConfig>> entrySet = dataSource.entrySet();
			for (Entry<String, SourceConfig> entry : entrySet) {
				sourceConfig = entry.getValue();
				break;
			}
		}
		return sourceConfig;
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
