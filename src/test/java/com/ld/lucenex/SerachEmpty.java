package com.ld.lucenex;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

import com.ld.lucenex.service.BasisService;

public class SerachEmpty {
	BasisService service = new BasisService("test");
	
	public void findList() throws IOException {
		service.findList(new TermQuery(new Term("text", "北京")), 10);
	}

}
