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

import com.ld.lucenex.core.LdDocument;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.util.DocumentUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import com.ld.lucenex.config.IndexSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: Service
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月23日 下午6:46:22
 */
public class Service{


	public IndexSource indexSource;

	public IndexWriter getIndexWriter(){
		if (indexSource == null) return null;
		return indexSource.getIndexWriter();
	}

	public IndexSearcher getIndexSearcher(){
		if (indexSource == null) return null;
		return this.indexSource.getIndexSearcher();
	}
	
    public Service(IndexSource indexSource){
    	this.indexSource = indexSource;
    }

	public Service(){
		this.indexSource = LuceneX.getInstance().getDefaultIndexSource();
	}

    public <T> long addDocuments(List<T> objects) throws IllegalAccessException, IOException {
		List<LdDocument> ldDocuments = DocumentUtil.toDocuments(objects);
		return getIndexWriter().addDocuments(ldDocuments);
	}

	public <T> long addDocument(T t) throws IllegalAccessException, IOException {
		LdDocument indexableFields = DocumentUtil.toDocument(t);
		return getIndexWriter().addDocument(indexableFields);
	}

	public Document[] search(Query query, int n) throws IOException{
		IndexSearcher indexSearcher = getIndexSearcher();
		TopDocs topDocs = indexSearcher.search(query, n);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		Document[] documents = new Document[scoreDocs.length];
		for (int i = 0; i < scoreDocs.length; i++){
			documents[i] = indexSearcher.doc(scoreDocs[i].doc);
		}
		return documents;
	}


	
    /**
     * 添加 docs
     * @param docs
     * @return
     * @throws IOException
     */
//    public long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
//        long addDocuments = indexSource.getIndexWriter().addDocuments(docs);
//        indexSource.updateIndexSource();
//        return addDocuments;
//    }
//
//    /**
//     * 添加 doc
//     * @param doc
//     * @return
//     * @throws IOException
//     */
//    public long addDocument(Iterable<? extends IndexableField> doc) throws IOException {
//        long document = indexSource.getIndexWriter().addDocument(doc);
//        indexSource.updateIndexSource();
//        return document;
//    }
//
//    /**
//     * 根据 term 更新
//     * @param documents
//     * @param term
//     * @return
//     * @throws IOException
//     */
//    public long updateDocuments(List<Document> documents, Term term) throws IOException {
//        long l = indexSource.getIndexWriter().updateDocuments(term, documents);
//        indexSource.updateIndexSource();
//        return l;
//    }
//
//    /**
//     * 根据 term 更新
//     * @param document
//     * @param term
//     * @return
//     * @throws IOException
//     */
//    public long updateDocument(Document document, Term term) throws IOException {
//        long l = indexSource.getIndexWriter().updateDocument(term, document);
//        indexSource.updateIndexSource();
//        return l;
//    }
//
//
//    /**
//     * 清空索引
//     * @return
//     * @throws IOException
//     */
//    public long deleteAll() throws IOException {
//        long deleteAll = indexSource.getIndexWriter().deleteAll();
//        indexSource.updateIndexSource();
//        return deleteAll;
//    }
//
//    /**
//     * 根据 query 删除
//     * @param queries
//     * @return
//     * @throws IOException
//     */
//    public long deleteDocuments(Query... queries) throws IOException {
//        long l = indexSource.getIndexWriter().deleteDocuments(queries);
//        indexSource.updateIndexSource();
//        return l;
//    }
//
//    /**
//     * 根据 term 删除
//     * @param terms
//     * @return
//     * @throws IOException
//     */
//    public long deleteDocuments(Term... terms) throws IOException {
//        long l = indexSource.getIndexWriter().deleteDocuments(terms);
//        indexSource.updateIndexSource();
//        return l;
//    }
//
//    /**
//     * 删除未使用的索引
//     * @throws IOException
//     */
//    public void deleteUnusedFiles() throws IOException {
//        indexSource.getIndexWriter().deleteUnusedFiles();
//        indexSource.updateIndexSource();
//    }
}
