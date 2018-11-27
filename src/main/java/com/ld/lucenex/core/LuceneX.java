package com.ld.lucenex.core;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;

public class LuceneX {

	private static final LuceneX luceneX = new LuceneX();
	private LuceneX() {
	}
	public static LuceneX build() {
		synchronized (luceneX) {
			return luceneX;
		}
	}
	
	public static void start(Class<?> clas) {
		try {
			LuceneXConfig config = (LuceneXConfig) clas.newInstance();
			BaseConfig.configLuceneX(config);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
