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

import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.core.ManySource;
import com.ld.lucenex.interce.impl.HIGIterface;
import com.ld.lucenex.interce.impl.NRTIterface;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.NoLockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logger.warn("运行结束保存所有数据");
            LuceneX.closeAll();
        }));
        constants.addInterface(0, new NRTIterface());
        constants.addInterface(1, new HIGIterface());
        config.configConstant(constants);
        config.configLuceneX(baseConfig);
    }

    public static Constants baseConfig() {
        return constants;
    }

    public void createSource(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer,
                             Class<?> clas) {
        try {
            SourceConfig config = new SourceConfig();
            if (StringUtils.isBlank(indexPath)) {
                if (StringUtils.isNotBlank(constants.getDefaultDisk())) {
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
            config.setHighlight(highlight);
            config.setDefaultClass(clas);
            config.setAnalyzer(analyzer);
            setWriter(config, indexDirectory.toPath());
            config.restartReader();
            ManySource.putDataSource(dataKey, config);
        } catch (Exception e) {
            logger.error("BaseConfig.createSource error", e);
        }
    }

    private void setWriter(SourceConfig config, Path path) throws IOException {
        FSDirectory directory = NIOFSDirectory.open(path, NoLockFactory.INSTANCE);
        IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(config.getAnalyzer()));
        config.setWriter(writer);
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
