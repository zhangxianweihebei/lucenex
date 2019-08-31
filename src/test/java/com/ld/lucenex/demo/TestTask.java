package com.ld.lucenex.demo;

import com.alibaba.fastjson.JSON;
import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Const;
import com.ld.lucenex.base.Page;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.query.QueryParser;
import com.ld.lucenex.service.ServiceFactory;
import com.ld.lucenex.service.ServiceImpl;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestTask {

    @BeforeAll
    public static void start() {
//        Const.FSD_TYPE = "nio";
        //推荐方式
        new LuceneX(new LuceneXConfig() {
            @Override
            public void configLuceneX(BaseConfig me) {
                me.add("d:/","test",Empty.class);
            }
        });
    }

    /**
     * 测试 10条数据是否成功
     * @throws IOException
     */
    @Test
    public void testSave() throws IOException {
        List<Empty> empties = new ArrayList<>(10);
        for (int i=0;i<10;i++){
            Empty empty = new Empty();
            empty.setId(i);
            empty.setName("新闻");
            empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
            empties.add(empty);
        }
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObjects(empties);
        List<Empty> emptyList = basisService.searchList(new TermQuery(new Term("name","新闻")));
        Assertions.assertTrue(emptyList.size() == 10);
    }

    /**
     * 测试 id 查询
     * @throws IOException
     */
    @Test
    public void testselectById() throws IOException {
            Empty empty = new Empty();
            empty.setId(888);
            empty.setName("新闻888");
            empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObject(empty);
        Empty searchOne = basisService.searchOne(IntPoint.newExactQuery("id", 888));
        Assertions.assertTrue(searchOne.getId() == empty.getId());
    }
    /**
     * 测试 name 查询
     * @throws IOException
     */
    @Test
    public void testselectByName() throws IOException {
            Empty empty = new Empty();
            empty.setId(999);
            empty.setName("百度一下");
            empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObject(empty);
        Empty searchOne = basisService.searchOne(new TermQuery(new Term("name","百度一下")));
        Assertions.assertTrue(searchOne.getId() == empty.getId());
    }

    /**
     * 测试 高亮 查询
     * @throws IOException
     */
    @Test
    public void testselectHighlightByName() throws IOException, ParseException {
        Empty empty = new Empty();
        empty.setId(999);
        empty.setName("百度一下");
        empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObject(empty);
        List<Empty> empties = basisService.searchList("人工智能", "text");
        System.out.println(JSON.toJSONString(empties));
//        Assertions.assertTrue(searchOne.getId() == empty.getId());
    }
    /**
     * 测试 多条件查询 id 必须等于10001 text 必须包含 人工智能
     * @throws IOException
     */
    @Test
    public void testselectByText() throws IOException {
        Empty empty = new Empty();
        empty.setId(10001);
        empty.setName("百度一下");
        empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObject(empty);
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(new String[]{"text"}, LuceneX.getIndexSource(Const.DEFAULT_SERVICE_KEY).getAnalyzer());
        try {
            Query query = multiFieldQueryParser.parse("人工智能");

            Query build = new QueryParser().addQuery(IntPoint.newExactQuery("id", 10001), BooleanClause.Occur.MUST)
                    .addQuery(query, BooleanClause.Occur.MUST).build();
            List<Empty> empties = basisService.searchList(build);
            Assertions.assertTrue(empties.size() > 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 分页
     * @throws IOException
     */
    @Test
    public void testPage() throws IOException, ParseException {
        List<Empty> empties = new ArrayList<>(10);
        for (int i=1;i<=100;i++){
            Empty empty = new Empty();
            empty.setId(i);
            empty.setName("testPage");
            empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
            empties.add(empty);
        }
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObjects(empties);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(1, 10)).getList().get(9).getId() == 10);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(2, 10)).getList().get(9).getId() == 20);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(3, 10)).getList().get(9).getId() == 30);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(4, 10)).getList().get(9).getId() == 40);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(5, 10)).getList().get(9).getId() == 50);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(6, 10)).getList().get(9).getId() == 60);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(7, 10)).getList().get(9).getId() == 70);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(8, 10)).getList().get(9).getId() == 80);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(9, 10)).getList().get(9).getId() == 90);
        Assertions.assertTrue(basisService.searchList(new TermQuery(new Term("name","testPage")), new Page<Empty>(10, 10)).getList().get(9).getId() == 100);
    }

    @AfterAll
    public static void stop() throws IOException {
        ServiceFactory.getService(ServiceImpl.class,"test").deleteAll();
    }
}
