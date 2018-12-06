package com.ld.lucenex.service;

import com.ld.lucenex.base.Page;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

import java.io.IOException;
import java.util.List;

public class BasisService implements Service {

    /**
     * @param list
     * @throws IOException
     * @Title: addIndex
     * @Description: 添加索引 支持 Object & map
     * @return: void
     */
    public void addIndex(List<?> list) throws IOException {
        List<Document> document = toDocument(list);
        addDocuments(document);
    }

    ;

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
        TopScoreDocCollector collector = TopScoreDocCollector.create(pageNum + pageSize);
        config.getSearcher().search(query, collector);
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
}
