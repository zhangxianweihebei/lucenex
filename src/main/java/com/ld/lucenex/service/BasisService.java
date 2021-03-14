//package com.ld.lucenex.service;
//
//import com.ld.lucenex.base.Page;
//import com.ld.lucenex.core.MyDocument;
//import com.ld.lucenex.util.CommonUtil;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.IntPoint;
//import org.apache.lucene.search.*;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//abstract class BasisService<T> extends Service {
//
//    public BasisService(String sourceKey) {
//        super(sourceKey);
//    }
//
//    /**
//     * 添加集合对象 支持 Java 实体类
//     * @param list
//     * @return
//     * @throws IOException
//     */
//    public long addObjects(List<?> list) throws IOException {
//        List<Field> declaredFields = indexSource.getDeclaredFields();
//        List<MyDocument> documents = list.stream().map(e -> new MyDocument(e, declaredFields)).collect(Collectors.toList());
//        return addDocuments(documents);
//    }
//
//    /**
//     * 添加对象 支持 Java 实体类
//     * @param object
//     * @return
//     * @throws IOException
//     */
//    public long addObject(Object object) throws IOException {
//        return addDocument(new MyDocument(object, indexSource.getDeclaredFields()));
//    }
//
//    /**
//     * 查询单个
//     * @param query
//     * @return
//     * @throws IOException
//     */
//    public Document searchOneDoc(Query query) throws IOException {
//        ScoreDoc[] scoreDocs = search(query, 10).scoreDocs;
//        if(scoreDocs.length == 0) return null;
//        if(scoreDocs.length > 1){
//            throw new RuntimeException("Expectations are one, results are multiple");
//        }
//        return getDocument(scoreDocs[0].doc);
//    }
//
//    /**
//     * 查询单个
//     * @param query
//     * @return
//     * @throws IOException
//     */
//    public T searchOne(Query query) throws IOException {
//        Document document = searchOneDoc(query);
//        if (document == null){
//            return null;
//        }
//        Class<T> defaultClass = indexSource.getDefaultClass();
//        return CommonUtil.getObject(document, defaultClass);
//    }
//
//    /**
//     * 根据 Query 查询集合
//     * @param query
//     * @param num
//     * @return List<Document>
//     * @throws IOException
//     */
//    public List<Document> searchListDoc(Query query, int num) throws IOException {
//        ScoreDoc[] scoreDocs = search(query, num).scoreDocs;
//        return getDocuments(scoreDocs);
//    }
//
//    /**
//     * 根据 Query 查询集合
//     * @param query
//     * @param num
//     * @return List<T>
//     * @throws IOException
//     */
//    public List<T> searchList(Query query, int num) throws IOException {
//        List<Document> documents = searchListDoc(query, num);
//        Class<T> defaultClass = indexSource.getDefaultClass();
//        return CommonUtil.getObjects(documents,defaultClass,query,indexSource);
//    }
//    /**
//     * 根据 Query 查询集合
//     * @param query
//     * @return List<T>
//     * @throws IOException
//     */
//    public List<T> searchList(Query query) throws IOException {
//        return searchList(query,Integer.MAX_VALUE);
//    }
//
//    /**
//     * @param query
//     * @param page  = Page.newPage(1, 10)
//     * @return
//     * @throws IOException
//     * @Title: searchList
//     * @Description: 简单分页查询
//     * @return: Page<Document>
//     */
//    public Page<Document> searchListDoc(Query query, Page<Document> page) throws IOException {
//        int pageSize = page.getPageSize();
//        int pageNum = page.getPageNum();
//        TopScoreDocCollector collector = TopScoreDocCollector.create(pageNum , pageSize);
//        indexSource.getIndexSearcher().search(query, collector);
//        int totalHits = collector.getTotalHits();
//        ScoreDoc[] scoreDocs = collector.topDocs(pageNum, pageSize).scoreDocs;
//        page.setList(getDocuments(scoreDocs));
//        page.setTotalRow(totalHits);
//        return page;
//    }
//    /**
//     * @param query
//     * @param page  = Page.newPage(1, 10)
//     * @return
//     * @throws IOException
//     * @Title: searchList
//     * @Description: 简单分页查询
//     * @return: Page<Document>
//     */
//    public Page<T> searchList(Query query, Page<T> page) throws IOException {
//        int pageSize = page.getPageSize();
//        int pageNum = page.getPageNum();
//        TopScoreDocCollector collector = TopScoreDocCollector.create(pageNum+pageSize,Integer.MAX_VALUE);
//        indexSource.getIndexSearcher().search(query, collector);
//        int totalHits = collector.getTotalHits();
//        ScoreDoc[] scoreDocs = collector.topDocs(pageNum, pageSize).scoreDocs;
//        List<Document> documentPageList = getDocuments(scoreDocs);
//        Class<T> defaultClass = indexSource.getDefaultClass();
//        page.setList(CommonUtil.getObjects(documentPageList,defaultClass,query,indexSource));
//        page.setTotalRow(totalHits);
//        return page;
//    }
//
//    /**
//     * @return
//     * @throws IOException
//     * @Title: searchTotal
//     * @Description: 查询所有文档 必须使用 MyDocument 添加
//     * @return: List<Document>
//     */
//    public List<Document> searchTotalDoc() throws IOException {
//        Query query = IntPoint.newExactQuery("lucenex_total", 0);
//        return searchListDoc(query, Integer.MAX_VALUE);
//    }
//    /**
//     * @return
//     * @throws IOException
//     * @Title: searchTotal
//     * @Description: 查询所有文档 必须使用 MyDocument 添加
//     * @return: List<T>
//     */
//    public List<T> searchTotal() throws IOException {
//        List<Document> documents = searchTotalDoc();
//        Class<T> defaultClass = indexSource.getDefaultClass();
//        return CommonUtil.getObjects(documents,defaultClass);
//    }
//
//    /**
//     * 统计数量
//     * @param query
//     * @return
//     * @throws IOException
//     */
//    public int count(Query query) throws IOException {
//        return indexSource.getIndexSearcher().count(query);
//    }
//
//    /**
//     * 获取列表
//     * @param scoreDocs
//     * @return
//     * @throws IOException
//     */
//    public List<Document> getDocuments(ScoreDoc[] scoreDocs) throws IOException {
//        List<Document> documents = new ArrayList(scoreDocs.length);
//        for (int i = 0, size = scoreDocs.length; i < size; i++) {
//            Document document = getDocument(scoreDocs[i].doc);
//            documents.add(document);
//        }
//        return documents;
//    }
//
//    /**
//     * 根据docId获取
//     * @param docID
//     * @return
//     * @throws IOException
//     */
//    public Document  getDocument(int docID) throws IOException {
//        return indexSource.getIndexSearcher().doc(docID);
//    }
//
//    public TopDocs search(Query query, int n) throws IOException {
//        return indexSource.getIndexSearcher().search(query, n);
//    }
//}
