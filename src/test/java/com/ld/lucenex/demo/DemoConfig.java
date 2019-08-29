package com.ld.lucenex.demo;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Constants;
import com.ld.lucenex.config.LuceneXConfig;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class DemoConfig extends LuceneXConfig {


    @Override
    public void configConstant(Constants me) {

    }

    /**
     * 设置库
     * @param me
     */
    @Override
    public void configLuceneX(BaseConfig me) {
        me.add("d:/","123",false,Empty.class);
        me.add("d:/","456",false,Empty.class);
    }
}
