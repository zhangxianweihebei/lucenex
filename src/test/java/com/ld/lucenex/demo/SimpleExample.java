//package com.ld.lucenex.demo;
//
//import com.ld.lucenex.config.IndexSource;
//import com.ld.lucenex.config.LuceneXConfig;
//import com.ld.lucenex.core.LuceneX;
//import com.ld.lucenex.service.ServiceFactory;
//import com.ld.lucenex.service.ServiceImpl;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.IntPoint;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *常用写法大全
// *
// * lucene 内置 query 示例大全
// * TermQuery - 精确查询
// * BooleanQuery - 多条件查询
// * WildcardQuery - 通配符查询
// * PhraseQuery - 短语查询
// * PrefixQuery - 前缀查询
// * MultiPhraseQuery - 通用短语查询（参考PhraseQuery用例）
// * FuzzyQuery - 模糊查询
// * RegexpQuery - 正则表达式查询
// * TermRangeQuery
// * PointRangeQuery
// * ConstantScoreQuery
// * DisjunctionMaxQuery
// * MatchAllDocsQuery
// */
//public class SimpleExample {
//
//    private Logger logger = LoggerFactory.getLogger(SimpleExample.class);
//
//    /**
//     * start
//     * @throws IOException 
//     */
//    @BeforeEach
//    public void init() throws IOException {
//        LuceneX.getInstance().addIndexSource("", IndexSource.builder().indexPath("d:/123/test").build());
//    }
//
//    /**
//     * 测试近实时
//     * 插入100万条数据，立马调用查询 并查出结果
//     */
//    @Test
//    public void testNearRealTime() throws IOException {
//        List<Empty> empties = new ArrayList<>(100000);
//        for (int i =0;i<1000000;i++){
//            Empty empty = new Empty();
//            empty.setId(i);
//            empty.setName("张三");
//            empty.setText("我是 一个 正儿八经 的 小男孩");
//            empties.add(empty);
//        }
//        ServiceFactory.getService(ServiceImpl.class,"123").addObjects(empties);
//        Assertions.assertTrue(ServiceFactory.getService(ServiceImpl.class,"123").searchTotal().size() == 1000000);
//        ServiceFactory.getService(ServiceImpl.class,"123").deleteAll();
//        Assertions.assertTrue(ServiceFactory.getService(ServiceImpl.class,"123").searchTotal().size() == 0);
//    }
//
//
//    @Test
//    public void deleteAll() throws IOException {
//        ServiceImpl ServiceImpl = ServiceFactory.getService(ServiceImpl.class);
//        ServiceImpl.deleteAll();
//    }
//
//    @Test
//    public void save() throws IOException {
//        ServiceImpl service = ServiceFactory.getService(ServiceImpl.class,"123");
//        Empty empty = new Empty();
//        empty.setId(1);
//        empty.setName("张三");
//        empty.setText("我是 一个 正儿八经 的 小男孩");
//        service.addObject(empty);
//    }
//
//    @Test
//    public void searchOneDoc() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        Document doc = service.searchOneDoc(IntPoint.newExactQuery("id",1));
//        System.out.println(doc);
//    }
//
//    @Test
//    public void searchTotal() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        List<Document> doc = service.searchTotal();
//        doc.forEach(e->System.out.println(e));
//    }
//
//    /**
//     * PrefixQuery - 前缀匹配
//     * @throws IOException
//     */
//    @Test
//    public void searchListPrefixQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        PrefixQuery prefixQuery = new PrefixQuery(new Term("name", "张"));
//        List<Document> docs = service.searchList(prefixQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//    /**
//     * WildcardQuery - 通配符
//     * @throws IOException
//     */
//    @Test
//    public void searchListWildcardQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        WildcardQuery wildcardQuery = new WildcardQuery(new Term("name", "?三"));
//        List<Document> docs = service.searchList(wildcardQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//    /**
//     * FuzzyQuery - 模糊查询
//     * @throws IOException
//     */
//    @Test
//    public void searchListFuzzyQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("name", "张二"));
//        List<Document> docs = service.searchList(fuzzyQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//    /**
//     * TermQuery - 精确查询
//     * @throws IOException
//     */
//    @Test
//    public void searchListTermQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        TermQuery termQuery = new TermQuery(new Term("name", "张三"));
//        List<Document> docs = service.searchList(termQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//    /**
//     * BooleanQuery - 多条件
//     * MUST 必须
//     * FILTER 过滤
//     * SHOULD 应该
//     * MUST_NOT 不能
//     * @throws IOException
//     */
//    @Test
//    public void searchListBooleanQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        TermQuery termQuery = new TermQuery(new Term("name", "张三"));
//        BooleanQuery booleanQuery = new BooleanQuery.Builder()
//                .add(termQuery, BooleanClause.Occur.MUST)
//                .add(IntPoint.newExactQuery("id",1),BooleanClause.Occur.MUST)
//                .build();
//        List<Document> docs = service.searchList(booleanQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//    /**
//     * PhraseQuery - 短语查询
//     * @throws IOException
//     */
//    @Test
//    public void searchListPhraseQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        PhraseQuery phraseQuery = new PhraseQuery.Builder()
//                .setSlop(1)
//                .add(new Term("text", "woshi"))
//                .add(new Term("text", "zhengerbajing"))
//                .build();
//        List<Document> docs = service.searchList(phraseQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//    /**
//     * RegexpQuery - 正则表达式查询
//     * @throws IOException
//     */
//    @Test
//    public void searchListRegexpQuery() throws IOException {
//        ServiceImpl service =  ServiceFactory.getService(ServiceImpl.class,"123");
//        RegexpQuery regexpQuery = new RegexpQuery(new Term("name","*三"));
//        regexpQuery.getRegexp();
//        List<Document> docs = service.searchList(regexpQuery,10);
//        docs.forEach(e->{
//            System.out.println(e);
//        });
//    }
//}
