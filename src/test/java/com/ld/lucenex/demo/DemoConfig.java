package com.ld.lucenex.demo;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Constants;
import com.ld.lucenex.config.LuceneXConfig;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class DemoConfig extends LuceneXConfig {

    /**
     * 基础配置
     * @param me
     */
    @Override
    public void configConstant(Constants me) {
        me.setDefaultDisk("/Users/a1/");
        me.setDefaultClass(Empty.class);
    }

    /**
     * 设置库
     * @param me
     */
    @Override
    public void configLuceneX(BaseConfig me) {
        me.add("123");
        me.add("456");
    }
}
