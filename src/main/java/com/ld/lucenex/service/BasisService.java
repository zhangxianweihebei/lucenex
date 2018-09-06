package com.ld.lucenex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

import com.ld.lucenex.base.Page;
import com.ld.lucenex.core.Service;

public class BasisService extends Service{
	
	public BasisService() {
		// TODO 自动生成的构造函数存根
	}
	public BasisService(String dataKey) {
		super(dataKey);
	}

	/**
	 * @Title: addIndex
	 * @Description: 添加索引 支持 Object 和 map
	 * @param list
	 * @throws IOException
	 * @return: void
	 */
	public void addIndex(List<?> list) throws IOException {
		List<Document> document = toDocument(list);
		addDocuments(document);
	};

	/**
	 * @Title: searchList
	 * @Description: 简单query 带长度 n
	 * @param query
	 * @param n
	 * @return
	 * @throws IOException
	 * @return: List<Document>
	 */
	public List<Document> searchList(Query query, int n) throws IOException{
		ScoreDoc[] scoreDocs = search(query, n).scoreDocs;
		if(scoreDocs != null && scoreDocs.length > 0) {
			List<Document> list = new ArrayList<>(scoreDocs.length);
			for(int i=0;i<scoreDocs.length;i++) {
				list.add(getDocument(scoreDocs[i].doc));
			}
			return list;
		}
		highlighter = isHighlighter(query);
		return null;
	}

	/**
	 * @Title: searchList
	 * @Description: 简单分页查询 
	 * @param query
	 * @param page = Page.newPage(1, 10)
	 * @return
	 * @throws IOException
	 * @return: Page<Document>
	 */
	public <T> Page<Document> searchList(Query query,Page<Document> page) throws IOException{
		int pageSize = page.getPageSize();
		int pageNum = page.getPageNum();
		TopScoreDocCollector collector = TopScoreDocCollector.create(pageNum+pageSize);
		searcher.search(query, collector);
		int totalHits = collector.getTotalHits();
		ScoreDoc[] scoreDocs = collector.topDocs(pageNum, pageSize).scoreDocs;
		if(scoreDocs != null && scoreDocs.length > 0) {
			List<Document> list = new ArrayList<>(scoreDocs.length);
			for(int i=0;i<scoreDocs.length;i++) {
				list.add(getDocument(scoreDocs[i].doc));
			}
			page.setList(list);
			page.setTotalRow(totalHits);
		}
		highlighter = isHighlighter(query);
		return page;
	}

	/**
	 * @Title: searchTotal
	 * @Description: 查询所有文档 必须使用 addIndex 添加
	 * @return
	 * @throws IOException
	 * @return: List<Document>
	 */
	public List<Document> searchTotal() throws IOException{
		Query query = IntPoint.newExactQuery("lucenex_total", 0);
		return searchList(query, Integer.MAX_VALUE);
	}
}
