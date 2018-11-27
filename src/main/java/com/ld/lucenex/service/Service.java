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
package com.ld.lucenex.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ld.lucenex.base.ToDocument;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.core.ManySource;

/**
 * @ClassName: Service
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:46:22
 */
interface Service {

	public SourceConfig config = ManySource.getDataSource();
	Logger logger = LoggerFactory.getLogger(Service.class);
	default	public long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
		long addDocuments = config.getWriter().addDocuments(docs);
		return addDocuments;
	}
	/**
	 * @Title: addDocument
	 * @Description: TODO
	 * @param doc
	 * @return
	 * @throws IOException
	 * @return: long
	 */
	default public long addDocument(Iterable<? extends IndexableField> doc) throws IOException {
		long addDocument = config.getWriter().addDocument(doc);
		return addDocument;
	}

	/**
	 * @Title: count
	 * @Description: 计算与给定查询匹配的文档数量
	 * @param query
	 * @return
	 * @throws IOException
	 * @return: int
	 */
	default public int count(Query query) throws IOException {
		return config.getSearcher().count(query);
	}

	/**
	 * @Title: getDocument
	 * @Description: 根据文档ID 获取一个文档
	 * @param docID
	 * @return
	 * @throws IOException
	 * @return: Document
	 */
	default public Document getDocument(int docID) throws IOException {
		return config.getSearcher().doc(docID);
	}

	default public TopDocs search(Query query, int n) throws IOException{
		return config.getSearcher().search(query, n);
	}
	default public TopFieldDocs search(Query query, int n, Sort sort){
		return search(query, n, sort);
	}

	default public long deleteAll() throws IOException {
		long l = config.getWriter().deleteAll();
		return l;
	}

	default public long deleteDocuments(Query... queries) throws IOException {
		long l = config.getWriter().deleteDocuments(queries);
		return l;
	}

	default public long deleteDocuments(Term... terms) throws IOException {
		long l = config.getWriter().deleteDocuments(terms);
		return l;
	}

	default public void deleteUnusedFiles() throws IOException {
		config.getWriter().deleteUnusedFiles();
	}

	default public long updateIndex(List<Document> list,Term term) throws IOException{
		long l = config.getWriter().updateDocuments(term, list);
		return l;
	}
	default public List<Document> toDocument(List<?> list){
		return ToDocument.getDocuments(list, config.getDefaultClass());
//		return list.stream().map(e->ToDocument.getDocument(e,config.getDefaultClass())).collect(Collectors.toList());
	}

	/**
	 * @Title: goBack
	 * @Description: 回退数据 前提处于非开发模式
	 * @return: void
	 */
	default public void goBack() {
		try {
			config.getWriter().rollback();
			config.setWriter(config.getWriter());
			config.restartReader();
		} catch (IOException e) {
			logger.error("回退索引error", e);
		}
	}
}
