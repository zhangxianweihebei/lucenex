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
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.ToDocument;
import com.ld.lucenex.config.Constants;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.thread.LdThreadPool;

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
	public static final Constants constants = BaseConfig.baseConfig();

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
		ManySource.putContextHolder(dataKey);
		config = ManySource.getDataSource();
		if(config != null) {
			searcher = config.getSearcher();
			writer = config.getWriter();
		}
	}

	/**
	 * @Title: addDocuments
	 * @Description: TODO
	 * @param docs
	 * @return
	 * @throws IOException
	 * @return: long
	 */
	public long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
		long addDocuments = writer.addDocuments(docs);
		Refresh();
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
	public long addDocument(Iterable<? extends IndexableField> doc) throws IOException {
		long addDocument = writer.addDocument(doc);
		Refresh();
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
	public int count(Query query) throws IOException {
		return searcher.count(query);
	}

	/**
	 * @Title: getDocument
	 * @Description: 根据文档ID 获取一个文档
	 * @param docID
	 * @return
	 * @throws IOException
	 * @return: Document
	 */
	public Document getDocument(int docID) throws IOException {
		return searcher.doc(docID);
	}

	public TopDocs search(Query query, int n) throws IOException{
		return searcher.search(query, n);
	}
	public TopFieldDocs search(Query query, int n, Sort sort){
		return search(query, n, sort);
	}

	public long deleteAll() throws IOException {
		long l = writer.deleteAll();
		Refresh();
		return l;
	}

	public long deleteDocuments(Query... queries) throws IOException {
		long l = writer.deleteDocuments(queries);
		Refresh();
		return l;
	}

	public long deleteDocuments(Term... terms) throws IOException {
		long l = writer.deleteDocuments(terms);
		Refresh();
		return l;
	}

	public void deleteUnusedFiles() throws IOException {
		writer.deleteUnusedFiles();
		Refresh();
	}
	
	public long updateIndex(List<Document> list,Term term) throws IOException{
		long l = writer.updateDocuments(term, list);
		Refresh();
		return l;
	}
	public List<Document> toDocument(List<?> list){
		return list.stream().map(e->ToDocument.getDocument(e,config.getDefaultClass())).collect(Collectors.toList());
	}
	
	/**
	 * @Title: Refresh
	 * @Description: 异步实时刷新 源
	 * @return: void
	 */
	public void Refresh() {
		LdThreadPool.build().get().execute(()->{
			try {
				if(constants.isDevMode()) {//开发模式
					writer.commit();
				}
				config.setWriter(writer);
				config.restartReader();
				searcher = config.getSearcher();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * @Title: goBack
	 * @Description: 回退数据 前提处于非开发模式
	 * @return: void
	 */
	public void goBack() {
		try {
			writer.rollback();
			Refresh();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
