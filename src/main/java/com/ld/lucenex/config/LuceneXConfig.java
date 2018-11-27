package com.ld.lucenex.config;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Constants;

public abstract class LuceneXConfig {
	public abstract void configConstant(Constants me);
	public abstract void configLuceneX(BaseConfig me);
}
