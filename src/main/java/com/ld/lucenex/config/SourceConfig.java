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

import com.ld.lucenex.analyzer.Dic;

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
	private Dic dic;
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
	/**
	 * @return dic
	 */
	public Dic getDic() {
		return dic;
	}
	/**
	 * @param dic 要设置的 dic
	 */
	public void setDic(Dic dic) {
		this.dic = dic;
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
	/* (non Javadoc)
	 * @Title: toString
	 * @Description: TODO
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SourceConfig [indexPath=" + indexPath + ", highlight=" + highlight + ", writer=" + writer
				+ ", searcher=" + searcher + ", analyzer=" + analyzer + ", defaultClass=" + defaultClass + ", dic="
				+ dic + "]";
	}
	
	
	
}
