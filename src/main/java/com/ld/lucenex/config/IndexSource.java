/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: LucenexConfig.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.config
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:36:15
 * @version: V1.0
 */
package com.ld.lucenex.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.field.FieldKey;
import com.ld.lucenex.util.CommonUtil;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.highlight.Highlighter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.nio.ch.FileKey;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @ClassName: LucenexConfig
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:36:15
 */
public class IndexSource<T> {

    private String indexPath;
    private boolean highlight;
    private IndexWriter indexWriter;
    private IndexSearcher indexSearcher;
    private PerFieldAnalyzerWrapper analyzer;
    private Class<T> defaultClass;
    private Highlighter highlighter;
    List<Field> declaredFields;
    private Map<String, JSONObject> highlighterField = new HashMap<>();

    private volatile boolean lock = false;

    private Logger logger = LoggerFactory.getLogger(IndexSource.class);


    public IndexSource(){

    }

    /**
     *
     * @param indexPath
     * @param writer
     * @param searcher
     * @param analyzer
     * @param defaultClass
     */
    public IndexSource(String indexPath, IndexWriter writer, IndexSearcher searcher, PerFieldAnalyzerWrapper analyzer, Class<T> defaultClass){
        this.indexPath = indexPath;
        this.indexWriter = writer;
        this.indexSearcher = searcher;
        this.analyzer = analyzer;
        this.defaultClass = defaultClass;
        Field[] declaredFields = defaultClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        for (Field field:declaredFields){
            field.setAccessible(true);
            if (field.isAnnotationPresent(FieldKey.class)){
                fields.add(field);
                FieldKey fieldKey = field.getAnnotation(FieldKey.class);
                if (fieldKey.highlight()){
                    this.highlight = true;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tag",fieldKey.highlightTag());
                    jsonObject.put("num",fieldKey.highlightNum());
                    highlighterField.put(field.getName(),jsonObject);
                }
            }
        }
        this.declaredFields = fields;
    }

    public void updateIndexSource(){
        synchronized (this.indexSearcher){
            try {
                //进实时转换
                IndexSearcher indexSearcher = CommonUtil.createIndexSearcher(this.indexWriter);
                this.indexSearcher = indexSearcher;
                logger.info("updateIndexSource ok");
            } catch (IOException e) {
                logger.error("Near Real-Time updateIndexSource error",e);
            }finally {
                ListenableFuture<Void> listenableFuture = LuceneX.getExecutorService().submit((Callable<Void>) () -> {
                    logger.info("source commit ...");
                    indexWriter.flush();
                    indexWriter.commit();
                    return null;
                });
                Futures.addCallback(listenableFuture, new FutureCallback<Void>() {
                    @Override
                    public void onSuccess(@Nullable Void aVoid) {
                        setLock(false);
                        logger.info("source commit success");
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        setLock(false);
                        logger.error("{} - source commit error",indexPath,throwable);
                    }
                },LuceneX.getExecutorService());
            }
        }
    }

    public String getIndexPath() {
        return indexPath;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public IndexWriter getIndexWriter() {
        return indexWriter;
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    public PerFieldAnalyzerWrapper getAnalyzer() {
        return analyzer;
    }

    public Class<T> getDefaultClass() {
        return defaultClass;
    }

    public Highlighter getHighlighter() {
        return highlighter;
    }

    public List<Field> getDeclaredFields() {
        return declaredFields;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public Map<String, JSONObject> getHighlighterField() {
        return highlighterField;
    }
}
