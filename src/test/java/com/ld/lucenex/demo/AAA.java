package com.ld.lucenex.demo;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AAA {
    public static void main(String[] args) {
        Query query;
        CCJSqlParserManager pm = new CCJSqlParserManager();
        try {
            Statement statement = pm.parse(new StringReader("select * from user where id = 2 or name like '%a%' and a !=2 "));
            if (statement instanceof Select){
                query = select((Select) statement);
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public static Query select(Select select){
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        FromItem fromItem = plainSelect.getFromItem();
        Table table = (Table) fromItem; // table.getAlias().getName(), table.getName()

        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        selectBody.accept(new SelectVisitorAdapter(){
            @Override
            public void visit(PlainSelect plainSelect) {
                Expression where = plainSelect.getWhere();
                if (where != null) {
                    List<Map<BooleanClause.Occur, Expression>> expression = Expression(where);
                    System.out.println(expression.size());
                }
            }
        });

        return null;
    }

    public static List<Map<BooleanClause.Occur, Expression>> Expression(Expression expression){
        if (expression == null){
            return new ArrayList<>(0);
        }
        List<Map<BooleanClause.Occur, Expression>> list = new ArrayList<>();
        expression.accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(EqualsTo expr) {
                findEqualsTo(expr, list);
            }
            @Override
            public void visit(AndExpression expr) {
                findEqualsTo(expr, list);
                System.out.println();
            }
            @Override
            public void visit(OrExpression expr) {
                findEqualsTo(expr, list);
            }
        });
        return list;
    }

    public static List<Map<BooleanClause.Occur,Expression>> findEqualsTo(Expression expression,List<Map<BooleanClause.Occur,Expression>> list){
        if (expression instanceof AndExpression){
            AndExpression andExpression = (AndExpression) expression;
            findEqualsTo(andExpression.getLeftExpression(),list,BooleanClause.Occur.MUST);
            findEqualsTo(andExpression.getRightExpression(),list,BooleanClause.Occur.MUST);
        }else if (expression instanceof OrExpression){
            OrExpression orExpression = (OrExpression) expression;
            findEqualsTo(orExpression.getLeftExpression(),list,BooleanClause.Occur.SHOULD);
            findEqualsTo(orExpression.getRightExpression(),list,BooleanClause.Occur.SHOULD);
        }else if (expression instanceof EqualsTo){
            findEqualsTo(expression, list,BooleanClause.Occur.MUST);
        }else if (expression instanceof NotEqualsTo){
            findEqualsTo(expression, list,BooleanClause.Occur.MUST_NOT);
        }
        return list;
    }

    public static void findEqualsTo(Expression expression,List<Map<BooleanClause.Occur,Expression>> list,BooleanClause.Occur qccur){
        if (expression instanceof EqualsTo || expression instanceof LikeExpression || expression instanceof NotEqualsTo){
            Map<BooleanClause.Occur, Expression> map = new HashMap<>();
            map.put(qccur,expression);
            list.add(map);
        }else {
            findEqualsTo(expression,list);
        }
    }
}
