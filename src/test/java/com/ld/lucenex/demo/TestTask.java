package com.ld.lucenex.demo;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Const;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.BasisService;
import com.ld.lucenex.service.ServiceFactory;
import org.apache.lucene.document.IntPoint;
import org.junit.jupiter.api.*;

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
        BasisService<Empty> basisService = ServiceFactory.getService(BasisService.class);
        basisService.addObjects(empties);
        List<Empty> emptyList = basisService.searchTotal();
        Assertions.assertTrue(emptyList.size() == 10);
    }

    @Test
    public void testselectById() throws IOException {
            Empty empty = new Empty();
            empty.setId(888);
            empty.setName("新闻");
            empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
        BasisService<Empty> basisService = ServiceFactory.getService(BasisService.class);
        basisService.addObject(empty);
        Empty searchOne = basisService.searchOne(IntPoint.newExactQuery("id", 888));
        Assertions.assertTrue(searchOne.getId() == empty.getId());
    }

    @AfterAll
    public static void stop() throws IOException {
        ServiceFactory.getService(BasisService.class,"test").deleteAll();
    }
}
