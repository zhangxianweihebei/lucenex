package com.ld.lucenex.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

import com.ld.lucenex.core.LdService;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.BasisService;

public class Main {
	static BasisService basisService;
	public static void main(String[] args) throws IOException, InterruptedException {
		//启动实例化
		LuceneX.start(DemoConfig.class);
		BasisService basisService = LdService.newInstance(BasisService.class);
		basisService.deleteAll();
		basisService.addIndex(save());
		Thread.sleep(1000);
		List<Document> total = basisService.searchTotal();
		System.out.println(total.size());
	}
	public static List<Empty> save() throws IOException {
		List<Empty> list = new ArrayList<>();
		Empty empty = new Empty();
		for(int i=0;i<10;i++) {
			empty.setId(i);
			empty.setName("中华人民共和国");
			empty.setText("新华社北京5月23日电　中共中央总书记、国家主北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共北京5月23日电　中共中央总书记、国家主席、5月23日电　中共席、5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任中央军委主席、中央审计委员会主任习近平5月23日下午主持召开中");
			list.add(empty);
		}
		return list;
	}
}
