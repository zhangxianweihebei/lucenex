package com.ld.lucenex.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.core.DocumentToObject;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.core.ObjectToDocument;
import com.ld.lucenex.service.Service;
import com.ld.lucenex.util.DocumentUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

public class Main {

	public static void main(String[] args) throws IOException, IllegalAccessException, InterruptedException {
		LuceneX instance = LuceneX.getInstance();
		Empty empty = new Empty();
		empty.setId(1);
		empty.setName("name");
		empty.setText("text");

		instance.addIndexSource(IndexSource.builder().indexPath("D:\\eclipse-jee-2020-09-R-win32-x86_64\\eclipse\\test-workspace\\test3").build());

		Service service = new Service();
		service.addDocument(empty);
		TermQuery termQuery = new TermQuery(new Term("name","name"));
		Document[] search1 = service.search(termQuery, 10);
		for (Document doc : search1) {
		    System.out.println(doc.get("name"));
		}

			try {
				JSONObject ss = JSON.parseObject("ss");
			}catch (Exception e) {

			}
	}
}
