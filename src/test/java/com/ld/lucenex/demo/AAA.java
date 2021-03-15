package com.ld.lucenex.demo;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
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
            Statement statement = pm.parse(new StringReader("select * from user where id = 2 and name = 1 and a = 1 or b = 1"));
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
                    Expression(where,builder);
                }
            }
        });

        return null;
    }

    public static void Expression(Expression expression,BooleanQuery.Builder builder){
        if (expression == null){
            return;
        }
        expression.accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(EqualsTo expr) {
                Expression leftExpression = expr.getLeftExpression();
                String fieldName = ((Column) leftExpression).getColumnName();
                Expression rightExpression = expr.getRightExpression();
                rightExpression.accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visit(StringValue value) {
                        Term term = new Term(fieldName, value.getValue());
                        builder.add(new TermQuery(term), BooleanClause.Occur.MUST);
                    }

                    @Override
                    public void visit(LongValue value) {
                        Query query = LongPoint.newExactQuery(fieldName, value.getValue());
                        builder.add(query, BooleanClause.Occur.MUST);
                    }
                });
            }

            @Override
            public void visit(AndExpression expr) {
                List<Map<BooleanClause.Occur, EqualsTo>> equalsTo = findEqualsTo(expr, new ArrayList<>());
                System.out.println();
            }
            @Override
            public void visit(OrExpression expr) {
                List<Map<BooleanClause.Occur, EqualsTo>> equalsTo = findEqualsTo(expr, new ArrayList<>());
                System.out.println();
            }
        });
    }

    public static List<Map<BooleanClause.Occur,EqualsTo>> findEqualsTo(Expression expression,List<Map<BooleanClause.Occur,EqualsTo>> list){
        if (expression instanceof AndExpression){
            AndExpression andExpression = (AndExpression) expression;
            Expression leftExpression = andExpression.getLeftExpression();
            if (leftExpression instanceof EqualsTo){
                Map<BooleanClause.Occur, EqualsTo> map = new HashMap<>();
                map.put(BooleanClause.Occur.MUST,(EqualsTo)leftExpression);
                list.add(map);
            }else {
                findEqualsTo(leftExpression,list);
            }
            Expression rightExpression = andExpression.getRightExpression();
            if (rightExpression instanceof EqualsTo){
                Map<BooleanClause.Occur, EqualsTo> map = new HashMap<>();
                map.put(BooleanClause.Occur.MUST,(EqualsTo)rightExpression);
                list.add(map);
            }else {
                findEqualsTo(rightExpression,list);
            }
        }else if (expression instanceof OrExpression){
            OrExpression orExpression = (OrExpression) expression;
            Expression leftExpression = orExpression.getLeftExpression();
            if (leftExpression instanceof EqualsTo){
                Map<BooleanClause.Occur, EqualsTo> map = new HashMap<>();
                map.put(BooleanClause.Occur.SHOULD,(EqualsTo)leftExpression);
                list.add(map);
            }else {
                findEqualsTo(leftExpression,list);
            }
            Expression rightExpression = orExpression.getRightExpression();
            if (rightExpression instanceof EqualsTo){
                Map<BooleanClause.Occur, EqualsTo> map = new HashMap<>();
                map.put(BooleanClause.Occur.SHOULD,(EqualsTo)rightExpression);
                list.add(map);
            }else {
                findEqualsTo(rightExpression,list);
            }
        }
        return list;
    }
}
