package com.ld.lucenex;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.Constants;
import com.ld.lucenex.config.LuceneXConfig;

public class DemoConfig extends LuceneXConfig{

	@Override
	public void configConstant(Constants me) {
		// TODO 自动生成的方法存根
		me.setDefaultClass(Empty.class);
		me.setDefaultDisk("d:/");
	}

	@Override
	public void configLuceneX(BaseConfig me) {
		me.add("test");
	}

}
