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

import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.core.LuceneX;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: Service
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月23日 下午6:46:22
 */
public abstract class Service {

    public IndexSource indexSource;

    public Service(String sourceKey){
        indexSource = LuceneX.getIndexSource(sourceKey);
    }

    /**
     * 添加 docs
     * @param docs
     * @return
     * @throws IOException
     */
    public long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
        long addDocuments = indexSource.getIndexWriter().addDocuments(docs);
        indexSource.updateIndexSource();
        return addDocuments;
    }

    /**
     * 添加 doc
     * @param doc
     * @return
     * @throws IOException
     */
    public long addDocument(Iterable<? extends IndexableField> doc) throws IOException {
        long document = indexSource.getIndexWriter().addDocument(doc);
        indexSource.updateIndexSource();
        return document;
    }

    /**
     * 根据 term 更新
     * @param docs
     * @param delTerm
     * @return
     * @throws IOException
     */
    public long updateDocuments(Term delTerm, Iterable<? extends Iterable<? extends IndexableField>> docs) throws IOException {
        long l = indexSource.getIndexWriter().updateDocuments(delTerm, docs);
        indexSource.updateIndexSource();
        return l;
    }

    /**
     * 根据 term 更新
     * @param doc
     * @param term
     * @return
     * @throws IOException
     */
    public long updateDocument(Term term, Iterable<? extends IndexableField> doc) throws IOException {
        long l = indexSource.getIndexWriter().updateDocument(term, doc);
        indexSource.updateIndexSource();
        return l;
    }


    /**
     * 清空索引
     * @return
     * @throws IOException
     */
    public long deleteAll() throws IOException {
        long deleteAll = indexSource.getIndexWriter().deleteAll();
        indexSource.updateIndexSource();
        return deleteAll;
    }

    /**
     * 根据 query 删除
     * @param queries
     * @return
     * @throws IOException
     */
    public long deleteDocuments(Query... queries) throws IOException {
        long l = indexSource.getIndexWriter().deleteDocuments(queries);
        indexSource.updateIndexSource();
        return l;
    }

    /**
     * 根据 term 删除
     * @param terms
     * @return
     * @throws IOException
     */
    public long deleteDocuments(Term... terms) throws IOException {
        long l = indexSource.getIndexWriter().deleteDocuments(terms);
        indexSource.updateIndexSource();
        return l;
    }

    /**
     * 删除未使用的索引
     * @throws IOException
     */
    public void deleteUnusedFiles() throws IOException {
        indexSource.getIndexWriter().deleteUnusedFiles();
        indexSource.updateIndexSource();
    }
}
