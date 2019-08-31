/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: BaseConfig.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午12:30:22
 * @version: V1.0
 */
package com.ld.lucenex.base;

import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.util.CommonUtil;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @ClassName: BaseConfig
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午12:30:22
 */
public class BaseConfig implements InitConfig {

    private static Logger logger = LoggerFactory.getLogger(BaseConfig.class);

    static final BaseConfig baseConfig = new BaseConfig();
    public static void configLuceneX(LuceneXConfig config) {
        config.configLuceneX(baseConfig);
    }

    public <T> void createSource(String indexPath, String dataKey, PerFieldAnalyzerWrapper analyzer, Class<T> clazz) {
        if (indexPath == null || dataKey == null){
            throw new NullPointerException("There is no default disk");
        }
        File file = new File(String.join("", indexPath, dataKey));
        if (!file.exists()){
            boolean mkdirs = file.mkdirs();
            if (!mkdirs){
                throw new NullPointerException("no indexPath or dataKey :{}"+file.getPath());
            }
        }
        if (clazz == null){
            throw new NullPointerException("No default class");
        }
        if (analyzer == null){
            analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer());
        }
        try {
            IndexWriter indexWriter = CommonUtil.createIndexWriter(file.getPath(), analyzer);
            IndexSearcher indexSearcher = CommonUtil.createIndexSearcher(indexWriter);
            IndexSource indexSource = new IndexSource(file.getPath(),indexWriter,indexSearcher,analyzer,clazz);
            LuceneX.addIndexSource(dataKey,indexSource);
        } catch (Exception e) {
            logger.error("BaseConfig.createSource error", e);
        }
    }

    @Override
    public void add(String indexPath, String dataKey, Class<?> clas) {
        // TODO 自动生成的方法存根
        createSource(indexPath, dataKey, null, clas);

    }

    @Override
    public void add(String indexPath, String dataKey, PerFieldAnalyzerWrapper analyzer,
                    Class<?> clas) {
        createSource(indexPath, dataKey, analyzer, clas);
    }

}

interface InitConfig {
    void add(String indexPath, String dataKey, Class<?> clas);
    void add(String indexPath, String dataKey, PerFieldAnalyzerWrapper analyzer, Class<?> clas);
}
