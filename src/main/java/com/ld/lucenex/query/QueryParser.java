package com.ld.lucenex.query;

import org.apache.lucene.search.*;

public class QueryParser {

    BooleanQuery.Builder builder;

    public QueryParser(){
        builder = new BooleanQuery.Builder();
    }

    public Query build(){
        return builder.build();
    }

    /**
     * 增加一个 query
     * @param query
     * @param qccur
     * @return
     */
    public QueryParser addQuery(Query query, BooleanClause.Occur qccur){
        builder.add(query,qccur);
        return this;
    }
    /**
     * 术语查询
     * @param termQuery
     * @param qccur
     * @return
     */
    public QueryParser termQuery(TermQuery termQuery, BooleanClause.Occur qccur){
        builder.add(termQuery,qccur);
        return this;
    }

    /**
     * 通配符查询
     * @param wildcardQuery
     * @param qccur
     * @return
     */
    public QueryParser wildcardQuery(WildcardQuery wildcardQuery, BooleanClause.Occur qccur){
        builder.add(wildcardQuery,qccur);
        return this;
    }

    /**
     * phraseQuery
     * @param phraseQuery
     * @param qccur
     * @return
     */
    public QueryParser phraseQuery(PhraseQuery phraseQuery, BooleanClause.Occur qccur){
        builder.add(phraseQuery,qccur);
        return this;
    }

    /**
     * 前缀查询
     * @param prefixQuery
     * @param qccur
     * @return
     */
    public QueryParser prefixQuery(PrefixQuery prefixQuery, BooleanClause.Occur qccur){
        builder.add(prefixQuery,qccur);
        return this;
    }

    /**
     * 多短语查询
     * @param multiPhraseQuery
     * @param qccur
     * @return
     */
    public QueryParser multiPhraseQuery(MultiPhraseQuery multiPhraseQuery, BooleanClause.Occur qccur){
        builder.add(multiPhraseQuery,qccur);
        return this;
    }

    /**
     * 模糊查询
     * @param fuzzyQuery
     * @param qccur
     * @return
     */
    public QueryParser fuzzyQuery(FuzzyQuery fuzzyQuery, BooleanClause.Occur qccur){
        builder.add(fuzzyQuery,qccur);
        return this;
    }

    /**
     * regexpQuery
     * @param regexpQuery
     * @param qccur
     * @return
     */
    public QueryParser regexpQuery(RegexpQuery regexpQuery, BooleanClause.Occur qccur){
        builder.add(regexpQuery,qccur);
        return this;
    }

    /**
     * 术语范围查询
     * @param termRangeQuery
     * @param qccur
     * @return
     */
    public QueryParser termRangeQuery(TermRangeQuery termRangeQuery, BooleanClause.Occur qccur){
        builder.add(termRangeQuery,qccur);
        return this;
    }

    /**
     * 点范围查询
     * @param pointRangeQuery
     * @param qccur
     * @return
     */
    public QueryParser pointRangeQuery(PointRangeQuery pointRangeQuery, BooleanClause.Occur qccur){
        builder.add(pointRangeQuery,qccur);
        return this;
    }

    /**
     * 常量分数查询
     * @param constantScoreQuery
     * @param qccur
     * @return
     */
    public QueryParser constantScoreQuery(ConstantScoreQuery constantScoreQuery, BooleanClause.Occur qccur){
        builder.add(constantScoreQuery,qccur);
        return this;
    }

    /**
     * 分离Max Query
     * @param disjunctionMaxQuery
     * @param qccur
     * @return
     */
    public QueryParser disjunctionMaxQuery(DisjunctionMaxQuery disjunctionMaxQuery, BooleanClause.Occur qccur){
        builder.add(disjunctionMaxQuery,qccur);
        return this;
    }

    /**
     * 匹配所有文档查询
     * @param matchAllDocsQuery
     * @param qccur
     * @return
     */
    public QueryParser matchAllDocsQuery(MatchAllDocsQuery matchAllDocsQuery, BooleanClause.Occur qccur){
        builder.add(matchAllDocsQuery,qccur);
        return this;
    }
}
