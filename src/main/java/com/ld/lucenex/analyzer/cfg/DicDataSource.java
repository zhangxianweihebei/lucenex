package com.ld.lucenex.analyzer.cfg;





public class DicDataSource {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

	public static void setDatabaseType(String type) {
		contextHolder.set(type);
	}
	/**
	 * 获取当前线程的DatabaseType
	 * @return
	 */
	public static String getDatabaseType() {
		return contextHolder.get();
	}

	public static void clearDatabaseType() {
		contextHolder.remove();
	}

}
