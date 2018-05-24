package com.ld.lucenex.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanWeight;
import org.apache.lucene.search.spans.Spans;

import com.ld.lucenex.core.Service;

public class BasisService extends Service{

	public BasisService() {
		// TODO 自动生成的构造函数存根
	}
	public BasisService(String dataKey) {
		super(dataKey);
	}

	public void addIndex(List<?> list) throws IOException {
		addDocuments(toDocument(list));
	};
	
	public void findList(SpanNearQuery query,int num) throws IOException{
		IndexSearcher searcher = config.getSearcher();
		SpanWeight weight = query.createWeight(searcher, true, 0);
		List<LeafReaderContext> leaves = searcher.getIndexReader().getContext().leaves();
        for (LeafReaderContext leaf : leaves) {
            Spans spans = weight.getSpans(leaf, SpanWeight.Postings.POSITIONS);
            while (spans.nextDoc() != Spans.NO_MORE_DOCS) {
                Document doc = leaf.reader().document(spans.docID());
                while (spans.nextStartPosition() != Spans.NO_MORE_POSITIONS) {
                    System.out.println("doc id = " + spans.docID() + ", doc IDX= " + doc.get("IDX") + ", start position = " + spans.startPosition() + ", end " +
                            "position = " + spans.endPosition());
                }
            }
        }
	}

}
