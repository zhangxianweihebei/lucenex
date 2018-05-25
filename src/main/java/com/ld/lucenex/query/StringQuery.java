/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: StringQuery.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.query
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月25日 下午12:16:42
 * @version: V1.0  
 */
package com.ld.lucenex.query;

import java.io.IOException;

import org.apache.lucene.queryparser.surround.parser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.Weight;

/**
 * @ClassName: StringQuery
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月25日 下午12:16:42
 */
public class StringQuery extends Query{

	/* (non Javadoc)
	 * @Title: toString
	 * @Description: TODO
	 * @param field
	 * @return
	 * @see org.apache.lucene.search.Query#toString(java.lang.String)
	 */
	@Override
	public String toString(String field) {
		// TODO 自动生成的方法存根
		return null;
	}

	/* (non Javadoc)
	 * @Title: equals
	 * @Description: TODO
	 * @param obj
	 * @return
	 * @see org.apache.lucene.search.Query#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO 自动生成的方法存根
		return false;
	}

	/* (non Javadoc)
	 * @Title: hashCode
	 * @Description: TODO
	 * @return
	 * @see org.apache.lucene.search.Query#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO 自动生成的方法存根
		return 0;
	}
	
	/* (non Javadoc)
	 * @Title: createWeight
	 * @Description: TODO
	 * @param searcher
	 * @param needsScores
	 * @param boost
	 * @return
	 * @throws IOException
	 * @see org.apache.lucene.search.Query#createWeight(org.apache.lucene.search.IndexSearcher, boolean, float)
	 */
	@Override
	public Weight createWeight(IndexSearcher searcher, boolean needsScores, float boost) throws IOException {
		// TODO 自动生成的方法存根
		return super.createWeight(searcher, needsScores, boost);
	}
}
