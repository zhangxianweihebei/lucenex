package com.ld.lucenex.demo;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.fastjson.JSON;
import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.query.SqlTemplate;

import java.io.IOException;
import java.util.List;

public class Test {
    static LuceneX luceneX;
    static {
        luceneX = new LuceneX(new LuceneXConfig() {
            @Override
            public void configLuceneX(BaseConfig me) {
                me.add("D:\\data\\", "user", User.class);
            }
        });
    }
    public static void main(String[] a) throws IOException {
//        String sql = "update user set a=123 where id = 9";
        String sql = "select * from user where name=zxw and id = 1 and id = 2 and id = 2";

//        // 新建 MySQL Parser
//        SQLStatementParser parser = new MySqlStatementParser(sql);
//
//        // 使用Parser解析生成AST，这里SQLStatement就是AST
//        SQLStatement statement = parser.parseStatement();
//        // 使用visitor来访问AST
//        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//        statement.accept(visitor);
//        System.out.println(visitor.getColumns());
//        System.out.println(visitor.getOrderByColumns());
        List<Object> query = new SqlTemplate(sql).query();
        query.forEach(e->{
            System.out.println(JSON.toJSONString(e));
        });
    }
}
