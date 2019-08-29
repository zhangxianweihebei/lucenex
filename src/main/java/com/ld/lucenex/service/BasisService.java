package com.ld.lucenex.service;

import com.ld.lucenex.base.Page;
import com.ld.lucenex.core.MyDocument;
import com.ld.lucenex.util.CommonUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasisService extends Service {

    public BasisService(){
        super();
    }
    public BasisService(String key){
        super(key);
    }

    /**
     * 添加集合对象 支持 Java 实体类
     * @param list
     * @return
     * @throws IOException
     */
    public long addObjects(List<Object> list) throws IOException {
        Field[] declaredFields = indexSource.getDeclaredFields();
        List<MyDocument> documents = list.stream().map(e -> new MyDocument(e, declaredFields)).collect(Collectors.toList());
        return addDocuments(documents);
    }

    /**
     * 添加对象 支持 Java 实体类
     * @param object
     * @return
     * @throws IOException
     */
    public long addObject(Object object) throws IOException {
        Field[] declaredFields = indexSource.getDeclaredFields();
        return addDocument(new MyDocument(object, declaredFields));
    }

    public Document searchOneDoc(Query query) throws IOException {
        ScoreDoc[] scoreDocs = search(query, 10).scoreDocs;
        if(scoreDocs.length == 0) return null;
        if(scoreDocs.length > 1){
            throw new RuntimeException("Expectations are one, results are multiple");
        }
        return getDocument(scoreDocs[0].doc);
    }

    /**
     * @param query
     * @param n
     * @return
     * @throws IOException
     * @Title: searchList
     * @Description: 简单query 带长度 n
     * @return: List<Document>
     */
    public List<Document> searchList(Query query, int n) throws IOException {
        ScoreDoc[] scoreDocs = search(query, n).scoreDocs;
        return getDocuments(scoreDocs);
    }

    /**
     * @param query
     * @param page  = Page.newPage(1, 10)
     * @return
     * @throws IOException
     * @Title: searchList
     * @Description: 简单分页查询
     * @return: Page<Document>
     */
    public Page<Document> searchList(Query query, Page<Document> page) throws IOException {
        int pageSize = page.getPageSize();
        int pageNum = page.getPageNum();
        TopScoreDocCollector collector = TopScoreDocCollector.create(pageNum , pageSize);
        indexSource.getIndexSearcher().search(query, collector);
        int totalHits = collector.getTotalHits();
        ScoreDoc[] scoreDocs = collector.topDocs(pageNum, pageSize).scoreDocs;
        page.setList(getDocuments(scoreDocs));
        page.setTotalRow(totalHits);
        return page;
    }

    /**
     * @return
     * @throws IOException
     * @Title: searchTotal
     * @Description: 查询所有文档 必须使用 addIndex 添加
     * @return: List<Document>
     */
    public List<Document> searchTotal() throws IOException {
        Query query = IntPoint.newExactQuery("lucenex_total", 0);
        return searchList(query, Integer.MAX_VALUE);
    }

    /**
     * 统计数量
     * @param query
     * @return
     * @throws IOException
     */
    int count(Query query) throws IOException {
        return indexSource.getIndexSearcher().count(query);
    }

    /**
     * 获取列表
     * @param scoreDocs
     * @return
     * @throws IOException
     */
    List<Document> getDocuments(ScoreDoc[] scoreDocs) throws IOException {
        List<Document> documents = new ArrayList(scoreDocs.length);
        for (int i = 0, size = scoreDocs.length; i < size; i++) {
            documents.add(getDocument(scoreDocs[i].doc));
        }
        return documents;
    }

    /**
     * 获取列表
     * @param scoreDocs
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> getObjects(ScoreDoc[] scoreDocs) throws IOException {
        List<T> documents = new ArrayList(scoreDocs.length);
        Class<T> clazz = indexSource.getDefaultClass();
        for (int i = 0, size = scoreDocs.length; i < size; i++) {
            Document document = getDocument(scoreDocs[i].doc);
            documents.add(CommonUtil.getObject(document,clazz));
        }
        return documents;
    }

    /**
     * 根据docId获取
     * @param docID
     * @return
     * @throws IOException
     */
    Document getDocument(int docID) throws IOException {
        return indexSource.getIndexSearcher().doc(docID);
    }

    TopDocs search(Query query, int n) throws IOException {
        return indexSource.getIndexSearcher().search(query, n);
    }

    TopFieldDocs search(Query query, int n, Sort sort) {
        return search(query, n, sort);
    }
}
