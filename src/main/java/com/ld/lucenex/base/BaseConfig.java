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

    private static final Constants constants = new Constants();
    private static final BaseConfig baseConfig = new BaseConfig();

    public static void configLuceneX(LuceneXConfig config) {
        config.configConstant(constants);
        config.configLuceneX(baseConfig);
    }

    public static Constants baseConfig() {
        return constants;
    }

    public void createSource(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer,
                             Class<?> clas) {
        try {
            if (indexPath == null) {
                if (constants.getDefaultDisk() != null) {
                    indexPath = constants.getDefaultDisk();
                } else {
                    throw new NullPointerException("There is no default disk");
                }
            }
            String path = indexPath + dataKey;
            File indexDirectory = new File(path);
            if (!indexDirectory.exists()) {
                indexDirectory.mkdirs();
            }
            if (!indexDirectory.isDirectory()) {
                throw new Exception("Not a valid directory");
            }
            if (analyzer == null) {
                analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer());
            }
            if (clas == null && constants.getDefaultClass() == null) {
                throw new NullPointerException("No default class");
            }
            if (clas == null) {
                if (constants.getDefaultClass() == null) {
                    throw new NullPointerException("No default class");
                } else {
                    clas = constants.getDefaultClass();
                }
            }
            IndexWriter indexWriter = CommonUtil.createIndexWriter(path, analyzer);
            IndexSearcher indexSearcher = CommonUtil.createIndexSearcher(indexWriter);
            IndexSource indexSource = new IndexSource(path,highlight,indexWriter,indexSearcher,analyzer,clas);
            LuceneX.addIndexSource(dataKey,indexSource);
        } catch (Exception e) {
            logger.error("BaseConfig.createSource error", e);
        }
    }

    @Override
    public void add(String dataKey) {
        createSource(null, dataKey, constants.isHighlight(), null, null);
    }

    @Override
    public void add(String dataKey, Class<?> clas) {
        createSource(null, dataKey, constants.isHighlight(), null, clas);
    }

    @Override
    public void add(String indexPath, String dataKey) {
        createSource(indexPath, dataKey, constants.isHighlight(), null, null);
    }

    @Override
    public void add(String indexPath, String dataKey, Class<?> clas) {
        // TODO 自动生成的方法存根
        createSource(indexPath, dataKey, constants.isHighlight(), null, clas);

    }

    @Override
    public void add(String indexPath, String dataKey, boolean highlight) {
        // TODO 自动生成的方法存根
        createSource(indexPath, dataKey, highlight, null, null);

    }

    @Override
    public void add(String indexPath, String dataKey, boolean highlight, Class<?> clas) {
        // TODO 自动生成的方法存根
        createSource(indexPath, dataKey, highlight, null, clas);

    }

    @Override
    public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer) {
        createSource(indexPath, dataKey, highlight, analyzer, null);

    }

    @Override
    public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer,
                    Class<?> clas) {
        createSource(indexPath, dataKey, highlight, analyzer, clas);
    }

}

interface InitConfig {

    void add(String dataKey);

    void add(String dataKey, Class<?> clas);

    void add(String indexPath, String dataKey);

    void add(String indexPath, String dataKey, Class<?> clas);

    void add(String indexPath, String dataKey, boolean highlight);

    void add(String indexPath, String dataKey, boolean highlight, Class<?> clas);

    void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer);

    void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer, Class<?> clas);

}
