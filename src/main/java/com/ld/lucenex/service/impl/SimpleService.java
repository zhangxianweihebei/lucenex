/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: SimpleService.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.service.impl
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年7月24日 下午3:00:32
 * @version: V1.0
 */
package com.ld.lucenex.service.impl;

import com.ld.lucenex.service.BasisService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: SimpleService
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年7月24日 下午3:00:32
 */
public class SimpleService extends BasisService {

    /**
     * 查询集合  字段类型 为String/text
     * @Title: TermQuery
     * @Description: TODO
     * @param field
     * @param value
     * @param num
     * @return
     * @throws IOException
     * @return: List<Document>
     */
    public List<Document> TermQuery(String field, String value, int num) throws IOException {
        TermQuery query = new TermQuery(new Term(field, value));
        return searchList(query, num == 0 ? 10 : num);
    }

    /**
     * 精确删除 字段类型为int
     * @Title: IntDelete
     * @Description: TODO
     * @param field
     * @param value
     * @return
     * @throws IOException
     * @return: long
     */
    public long IntDelete(String field, int value) throws IOException {
        Query query = IntPoint.newExactQuery(field, value);
        return deleteDocuments(query);
    }

}
