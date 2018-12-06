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
    }

    /**
     * 设置库
     * @param me
     */
    @Override
    public void configLuceneX(BaseConfig me) {
        // 存储目录 、名称、高亮、分词器、存储类
        me.add("d:/", "test", false, new PerFieldAnalyzerWrapper(new StandardAnalyzer()), Empty.class);
    }
}
