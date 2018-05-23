package com.ld.lucenex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ld.lucenex.service.BasisService;

public class SaveIndex {
	
	BasisService service = new BasisService("test");
	
	public void save() throws IOException {
		List<Empty> list = new ArrayList<>();
		for(int i=0;i<10;i++) {
			Empty empty = new Empty();
			empty.setId(i);
			empty.setName("习近平主持召开中央审计委员会第一次会议强调");
			empty.setText("新华社北京5月23日电　中共中央总书记、国家主席、中央军委主席、中央审计委员会主任习近平5月23日下午主持召开中");
			list.add(empty);
		}
		service.saveIndex(list);
	}
	
}
