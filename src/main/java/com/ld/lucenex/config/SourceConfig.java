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

import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.highlight.Highlighter;

import java.io.IOException;

/**
 * @ClassName: LucenexConfig
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:36:15
 */
public class SourceConfig {
    private String indexPath;
    private boolean highlight;
    private IndexWriter writer;
    private IndexSearcher searcher;
    private PerFieldAnalyzerWrapper analyzer;
    private Class<?> defaultClass;
    private Highlighter highlighter;


    /**
     * 设置高亮对象
     * @param highlighter
     */
    public void setHighlighter(Highlighter highlighter) {
        this.highlighter = highlighter;
    }

    /**
     * 获取高亮对象
     * @return
     */
    public Highlighter getHighlighter() {
        return highlighter;
    }

    /**
     * 获取索引目录
     * @return
     */
    public String getIndexPath() {
        return indexPath;
    }

    /**
     * 设置索引目录
     * @param indexPath
     */
    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    /**
     * 判断是否开启高亮 true 开启 false 关闭
     * false 情况下Highlighter 高亮对象等于空
     * @return
     */
    public boolean isHighlight() {
        return highlight;
    }

    /**
     * 设置是否高亮
     * @param highlight
     */
    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    /**
     * 获取一个写入源
     * @return
     */
    public IndexWriter getWriter() {
        return writer;
    }

    /**
     * 设置一个写入源
     * @param writer
     */
    public void setWriter(IndexWriter writer) {
        this.writer = writer;
    }

    /**
     * 获取一个检索源
     * @return
     */
    public IndexSearcher getSearcher() {
        return searcher;
    }

    /**
     * 设置一个检索源
     * @param searcher
     */
    public void setSearcher(IndexSearcher searcher) {
        this.searcher = searcher;
    }

    /**
     * 获取分词器 PerFieldAnalyzerWrapper
     * @return
     */
    public PerFieldAnalyzerWrapper getAnalyzer() {
        return analyzer;
    }

    /**
     * 设置一个分词器
     * @param analyzer
     */
    public void setAnalyzer(PerFieldAnalyzerWrapper analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * 获取索引类 Class 用于读取字段
     * @return
     */
    public Class<?> getDefaultClass() {
        return defaultClass;
    }

    /**
     * 设置一个索引类 Class
     * @param defaultClass
     */
    public void setDefaultClass(Class<?> defaultClass) {
        this.defaultClass = defaultClass;
    }

    /**
     * 刷新检索源
     * @throws IOException
     */
    public void restartReader() throws IOException {
        try {
            DirectoryReader reader = DirectoryReader.open(this.writer);
            IndexSearcher indexSearcher = new IndexSearcher(reader);
            setSearcher(indexSearcher);
        } catch (IOException e) {
            throw new IOException("Write conversion read error");
        }
    }
}
