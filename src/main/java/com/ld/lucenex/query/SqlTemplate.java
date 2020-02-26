package com.ld.lucenex.query;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.ld.lucenex.service.ServiceFactory;
import com.ld.lucenex.service.ServiceImpl;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.alibaba.druid.sql.ast.expr.SQLBinaryOperator.BooleanAnd;

public class SqlTemplate {

    SQLStatementParser parser;
    SQLStatement statement;
    MySqlSchemaStatVisitor visitor;

    public SqlTemplate(String sql){
        parser = new MySqlStatementParser(sql);
        statement = parser.parseStatement();
        visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);
    }

    public <T> List<T> query() throws IOException {
        QueryParser queryParser = new QueryParser();
        Map<TableStat.Name, TableStat> tables = visitor.getTables();
        String name = tables.keySet().iterator().next().getName();
        ServiceImpl service = ServiceFactory.getService(ServiceImpl.class, name);
        if (statement instanceof SQLSelectStatement){
            SQLSelectStatement sqlSelectStatement = (SQLSelectStatement)statement;
            SQLSelect sqlSelect = sqlSelectStatement.getSelect();
            SQLSelectQuery sqlSelectQuery = sqlSelect.getQuery();
            if (sqlSelectQuery instanceof MySqlSelectQueryBlock){
                MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock)sqlSelectQuery;
                SQLBinaryOpExpr sqlExpr = (SQLBinaryOpExpr)mySqlSelectQueryBlock.getWhere();
                where(queryParser,mySqlSelectQueryBlock);
                Query query = createQuery(sqlExpr.getLeft(), sqlExpr.getRight(), sqlExpr.getOperator(), queryParser);
//                List<SQLObject> whereChildren = where.getChildren();
//                for (SQLObject sqlObject:whereChildren
//                     ) {
//                    if (sqlObject instanceof SQLBinaryOpExpr){
//                        SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr)sqlObject;
//                        if (sqlBinaryOpExpr.getOperator() == SQLBinaryOperator.Equality){
//                            queryParser.termQuery(new TermQuery(new Term(sqlBinaryOpExpr.getLeft().toString(),sqlBinaryOpExpr.getRight().toString())), BooleanClause.Occur.MUST);
//                        }
//                    }
//                    System.out.println("0");
//                }
            }
        }
        return (List<T>) service.searchList(queryParser.build(),100);
    }

    public void where(QueryParser queryParser,MySqlSelectQueryBlock sqlSelectQueryBlock){
        SQLExpr where = sqlSelectQueryBlock.getWhere();
        if (where instanceof SQLBinaryOpExpr){
        }

    }


    private Query createQuery(SQLExpr left,SQLExpr right,SQLBinaryOperator sqlBinaryOperator,QueryParser queryParser){
        if (sqlBinaryOperator == BooleanAnd){

        }
        switch (sqlBinaryOperator){
            case Equality:
                TermQuery termQuery = new TermQuery(new Term(left.toString(),right.toString()));
                return termQuery;
            case BooleanAnd:
                SQLBinaryOpExpr l = (SQLBinaryOpExpr)left;
                SQLBinaryOpExpr r = (SQLBinaryOpExpr)right;
                queryParser.addQuery(createQuery(l.getLeft(),l.getRight(),l.getOperator(),queryParser),BooleanClause.Occur.MUST);
                queryParser.addQuery(createQuery(r.getLeft(),r.getRight(),r.getOperator(),queryParser),BooleanClause.Occur.MUST);
                break;
        }
        return null;

    }

    private Query createQuery(SQLBinaryOpExpr left,SQLBinaryOpExpr right,SQLBinaryOperator sqlBinaryOperator,QueryParser queryParser){
        if (left.getLeft() instanceof SQLBinaryOpExpr){

        }
        return null;

    }
}
