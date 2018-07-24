package com.ld.lucenex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.BasisService;
import com.ld.lucenex.service.impl.SimpleService;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		LuceneX.start(DemoConfig.class);
		new BasisService("test").deleteAll();
		save();
		List<Document> list = new SimpleService("test").TermQuery("text", "北京", 100);
//		Page<Document> page = new BasisService("test")
//				.searchList(new TermQuery(new Term("text", "北京")), Page.newPage(1, 5));
//		System.out.println(page);
//		page.getList().forEach(e->System.out.println(e));
//	
//		//删除一个
//		new BasisService("test").deleteDocuments(IntPoint.newExactQuery("id", 3));
//		Page<Document> page2 = new BasisService("test")
//				.searchList(new TermQuery(new Term("text", "北京")), Page.newPage(1, 5));
//		System.out.println(page2);
//		page2.getList().forEach(e->System.out.println(e));
		for (Document document : list) {
			System.out.println(document);
		}
	}
	public static void save() throws IOException {
		List<Empty> list = new ArrayList<>();
		for(int i=0;i<100;i++) {
			Empty empty = new Empty();
			empty.setId(i);
			empty.setName("中华人民共和国");
			empty.setText("新华社北京5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任习近平5月23日下午主持召开中");
			list.add(empty);
		}
		new BasisService("test").addIndex(list);
	}
}
