package com.ld.lucenex;

import static org.apache.lucene.search.spans.SpanNearQuery.newOrderedNearQuery;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanTermQuery;

import com.ld.lucenex.service.BasisService;

public class SerachEmpty {
	BasisService service = new BasisService("test");
	
	public void findList() throws IOException {
		 SpanTermQuery spanTermQuery1 = new SpanTermQuery(new Term("name", "中"));
		 SpanTermQuery spanTermQuery2 = new SpanTermQuery(new Term("name", "国"));
		 SpanNearQuery query = newOrderedNearQuery("name").addClause(spanTermQuery1).addClause(spanTermQuery2).build();
		service.findList(query, 10);
	}

}
