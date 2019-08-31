package com.ld.lucenex.service;

import com.ld.lucenex.base.Page;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.List;

public class ServiceImpl<T> extends BasisService<T>{

    public ServiceImpl(String sourceKey) {
        super(sourceKey);
    }

    public List<T> searchList(String query,String... fields) throws ParseException, IOException {
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, indexSource.getAnalyzer());
        Query parse = multiFieldQueryParser.parse(query);
        return searchList(parse);
    }
    public List<T> searchList(String query,int num,String... fields) throws ParseException, IOException {
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, indexSource.getAnalyzer());
        Query parse = multiFieldQueryParser.parse(query);
        return searchList(parse,num);
    }

    public Page<T> searchList(String query,Page<T> page,String... fields) throws ParseException, IOException {
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, indexSource.getAnalyzer());
        Query parse = multiFieldQueryParser.parse(query);
        return searchList(parse,page);
    }

}
