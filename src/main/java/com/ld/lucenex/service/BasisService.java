package com.ld.lucenex.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import com.ld.lucenex.base.ToDocument;
import com.ld.lucenex.core.Service;

public class BasisService extends Service{

	public BasisService() {
		// TODO 自动生成的构造函数存根
	}
	public BasisService(String dataKey) {
		super(dataKey);
	}

	public void saveIndex(List<?> list) throws IOException {
		List<Document> collect = list.stream().map(e->{
			Document document = null;
			try {
				document = ToDocument.getDocument(e);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			return document;
		}).collect(Collectors.toList());
		config.getWriter().addDocuments(collect);
		config.getWriter().commit();
	};
	
	public void findList(Query query,int num) throws IOException{
		IndexSearcher searcher = config.getSearcher();
		TopDocs topDocs = searcher.search(query, num);
		System.out.println();
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			System.out.println(searcher.doc(scoreDoc.doc));
		}
	}

}
