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

import java.io.IOException;

import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.highlight.Highlighter;
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
	 * @param highlighter 要设置的 highlighter
	 */
	public void setHighlighter(Highlighter highlighter) {
		this.highlighter = highlighter;
	}
	/**
	 * @return highlighter
	 */
	public Highlighter getHighlighter() {
		return highlighter;
	}
	public String getIndexPath() {
		return indexPath;
	}
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public IndexWriter getWriter() {
		return writer;
	}
	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}
	public IndexSearcher getSearcher() {
		return searcher;
	}
	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}
	public PerFieldAnalyzerWrapper getAnalyzer() {
		return analyzer;
	}
	public void setAnalyzer(PerFieldAnalyzerWrapper analyzer) {
		this.analyzer = analyzer;
	}
	/**
	 * @return defaultClass
	 */
	public Class<?> getDefaultClass() {
		return defaultClass;
	}
	/**
	 * @param defaultClass 要设置的 defaultClass
	 */
	public void setDefaultClass(Class<?> defaultClass) {
		this.defaultClass = defaultClass;
	}

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
