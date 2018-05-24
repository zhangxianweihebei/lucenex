/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: Service.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.core
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:46:22
 * @version: V1.0  
 */
package com.ld.lucenex.core;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.ToDocument;
import com.ld.lucenex.config.Constants;
import com.ld.lucenex.config.SourceConfig;

/**
 * @ClassName: Service
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:46:22
 */
public class Service {

	public SourceConfig config;
	public IndexWriter writer;
	public IndexSearcher searcher;
	private String dataKey="";
	private static final Constants constants = BaseConfig.baseConfig();

	/**
	 * @Title:Service
	 * @Description:TODO
	 */
	public Service() {
		// TODO 自动生成的构造函数存根
	}
	/**
	 * @Title:Service
	 * @Description:TODO
	 */
	public Service(String dataKey) {
		this.dataKey = dataKey;
		ManySource.putContextHolder(dataKey);
		config = ManySource.getDataSource();
		if(config != null) {
			searcher = config.getSearcher();
			writer = config.getWriter();
		}
	}

	public long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
		if(writer == null) {
			throw new NullPointerException("No relevant source "+dataKey);
		}
		long addDocuments = writer.addDocuments(docs);
		if(constants.isDevMode()) {//开发模式
			writer.commit();
		}
		config.restartReader();//实时
		return addDocuments;
	}
	public long addDocument(Iterable<? extends IndexableField> doc) throws IOException {
		if(writer == null) {
			throw new NullPointerException("No relevant source "+dataKey);
		}
		long addDocument = writer.addDocument(doc);
		if(constants.isDevMode()) {//开发模式
			writer.commit();
		}
		config.restartReader();//实时
		return addDocument;
	}
	
	public List<Document> toDocument(List<?> list){
		return list.stream().map(e->ToDocument.getDocument(e,config.getDefaultClass())).collect(Collectors.toList());
	}

}
