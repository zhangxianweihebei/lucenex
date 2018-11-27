package com.ld.lucenex.demo;

import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Constants;
import com.ld.lucenex.config.LuceneXConfig;

public class DemoConfig extends LuceneXConfig{

	@Override
	public void configConstant(Constants me) {
	}

	@Override
	public void configLuceneX(BaseConfig me) {
		// 存储目录 、名称、高亮、分词器、存储类
		me.add("d:/", "test",  false, new PerFieldAnalyzerWrapper(new StandardAnalyzer()), Empty.class);
	}
}
