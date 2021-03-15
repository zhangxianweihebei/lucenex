package com.ld.lucenex.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.Service;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, IllegalAccessException {
        LuceneX instance = LuceneX.getInstance();
        Empty empty = new Empty();
        empty.setId(1);
        empty.setName("name");
        empty.setText("text");

        instance.addIndexSource(IndexSource.builder().indexPath("D:\\eclipse-jee-2020-09-R-win32-x86_64\\eclipse\\test-workspace\\test3").build());

        Service service = new Service();
        service.addDocument(empty);
        TermQuery termQuery = new TermQuery(new Term("name", "name"));
        Document[] search1 = service.search(termQuery, 10);
        for (Document doc : search1) {
            System.out.println(doc.get("name"));
        }
        try {
            JSONObject ss = JSON.parseObject("ss");
        } catch (Exception e) {

        }
    }
}
