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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
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
 * @param <T>
 * @date: 2018年5月23日 下午6:46:22
 */
interface Service<T> {

    SourceConfig config = ManySource.getDataSource();
    Logger logger = LoggerFactory.getLogger(Service.class);

    default long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
        long addDocuments = config.getWriter().addDocuments(docs);
        return addDocuments;
    }

    /**
     * @param doc
     * @return
     * @throws IOException
     * @Title: addDocument
     * @Description: TODO
     * @return: long
     */
    default long addDocument(Iterable<? extends IndexableField> doc) throws IOException {
        long addDocument = config.getWriter().addDocument(doc);
        return addDocument;
    }

    /**
     * @param query
     * @return
     * @throws IOException
     * @Title: count
     * @Description: 计算与给定查询匹配的文档数量
     * @return: int
     */
    default int count(Query query) throws IOException {
        return config.getSearcher().count(query);
    }

    default List<Document> getDocuments(ScoreDoc[] scoreDocs) throws IOException {
        List<Document> documents = new ArrayList(scoreDocs.length);
        for (int i = 0, size = scoreDocs.length; i < size; i++) {
            documents.add(getDocument(scoreDocs[i].doc));
        }
        return documents;
    }
    
    default List<T> getObjects(ScoreDoc[] scoreDocs) throws IOException {
        List<T> documents = new ArrayList(scoreDocs.length);
        Class<?> defaultClass = config.getDefaultClass();
        for (int i = 0, size = scoreDocs.length; i < size; i++) {
            documents.add(ToDocument.documentToObject(defaultClass, getDocument(scoreDocs[i].doc)));
        }
        return documents;
    }

    /**
     * @param docID
     * @return
     * @throws IOException
     * @Title: getDocument
     * @Description: 根据文档ID 获取一个文档
     * @return: Document
     */
    default Document getDocument(int docID) throws IOException {
        return config.getSearcher().doc(docID);
    }

    default TopDocs search(Query query, int n) throws IOException {
        return config.getSearcher().search(query, n);
    }

    default TopFieldDocs search(Query query, int n, Sort sort) {
        return search(query, n, sort);
    }

    default long deleteAll() throws IOException {
        long l = config.getWriter().deleteAll();
        return l;
    }

    default long deleteDocuments(Query... queries) throws IOException {
        long l = config.getWriter().deleteDocuments(queries);
        return l;
    }

    default long deleteDocuments(Term... terms) throws IOException {
        long l = config.getWriter().deleteDocuments(terms);
        return l;
    }

    default void deleteUnusedFiles() throws IOException {
        config.getWriter().deleteUnusedFiles();
    }

    default long updateIndex(List<Document> list, Term term) throws IOException {
        long l = config.getWriter().updateDocuments(term, list);
        return l;
    }

    default List<Document> toDocument(List<?> list) {
        return ToDocument.getDocuments(list, config.getDefaultClass());
    }
    
    default Document toDocument(Object object) {
    	Field[] fields = config.getDefaultClass().getDeclaredFields();
        return ToDocument.getDocument(object, fields);
    }

    /**
     * @Title: goBack
     * @Description: 回退数据 前提处于非开发模式
     * @return: void
     */
    default void goBack() {
        try {
            config.getWriter().rollback();
            config.setWriter(config.getWriter());
            config.restartReader();
        } catch (IOException e) {
            logger.error("回退索引error", e);
        }
    }
}
