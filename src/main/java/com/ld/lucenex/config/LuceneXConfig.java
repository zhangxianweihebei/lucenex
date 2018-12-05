package com.ld.lucenex.config;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Constants;

public abstract class LuceneXConfig {
	/**
	 * 基础配置
	 * @param me
	 */
	public abstract void configConstant(Constants me);

	/**
	 * 数据源配置
	 * @param me
	 */
	public abstract void configLuceneX(BaseConfig me);
}
