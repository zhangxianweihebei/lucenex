package com.ld.lucenex.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ld.lucenex.base.Const;
import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.core.LuceneX;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonUtil {

    public static <T> T getObject(Document document,Class<T> clazz){
        JSONObject jsonObject = new JSONObject();
        document.forEach(e->{
            String name = e.name();
            String value = e.stringValue();
            jsonObject.put(name,value);
        });
        T t = jsonObject.toJavaObject(clazz);
        return t;
    }
    public static <T> List<T> getObjects(List<Document> documents, Class<T> clazz){
        return documents.stream().map(e->getObject(e,clazz)).collect(Collectors.toList());
    }
    public static <T> T getObject(Document document, Class<T> clazz, Query query, IndexSource indexSource,Map<String,Highlighter> highlighterMap){
        JSONObject jsonObject = new JSONObject();
        if (highlighterMap == null){
            document.forEach(e->{
                String name = e.name();
                String value = e.stringValue();
                jsonObject.put(name,value);
            });
        }else {
            document.forEach(e->{
                String name = e.name();
                String value = e.stringValue();
                Highlighter highlighter = highlighterMap.get(name);
                if (highlighter == null){
                    jsonObject.put(name,value);
                }else {
                    try {
                        String bestFragment = highlighter.getBestFragment(indexSource.getAnalyzer(), name, value);
                        jsonObject.put(name,bestFragment);
                    } catch (IOException | InvalidTokenOffsetsException ex) {
                        jsonObject.put(name,value);
                        ex.printStackTrace();
                    }
                }
            });
        }
        T t = jsonObject.toJavaObject(clazz);
        return t;
    }
    public static <T> List<T> getObjects(List<Document> documents, Class<T> clazz, Query query, IndexSource indexSource){
        if (indexSource.isHighlight()){
            Map<String, JSONObject> highlighterField = indexSource.getHighlighterField();
            Map<String,Highlighter> highlighterMap = new HashMap<>();
            QueryScorer scorer = new QueryScorer(query);
            highlighterField.forEach((k,v)->{
                JSONArray tag = v.getJSONArray("tag");
                int num = v.getIntValue("num");
                Formatter formatter = new SimpleHTMLFormatter(tag.getString(0),tag.getString(1));
                Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, num);
                Highlighter highlighter = new Highlighter(formatter,scorer);
                highlighter.setTextFragmenter(fragmenter);
                highlighterMap.put(k,highlighter);
            });

            return documents.stream().map(e->getObject(e,clazz,query,indexSource, highlighterMap)).collect(Collectors.toList());
        }else {
            return documents.stream().map(e->getObject(e,clazz,query,indexSource, null)).collect(Collectors.toList());
        }
    }

    /**
     * 创建写入索引
     * @param indexPath
     * @param analyzer
     * @return
     * @throws IOException
     */
    public static IndexWriter createIndexWriter(String indexPath, Analyzer analyzer) throws IOException {
        FSDirectory fsDirectory = null;
        switch (Const.FSD_TYPE){
            case "nio":
                fsDirectory = NIOFSDirectory.open(new File(indexPath).toPath(), NoLockFactory.INSTANCE);
                break;
            case "simp":
                fsDirectory = SimpleFSDirectory.open(new File(indexPath).toPath(), NoLockFactory.INSTANCE);
                break;
            case "mmap":
                fsDirectory = MMapDirectory.open(new File(indexPath).toPath(), NoLockFactory.INSTANCE);
                break;
            default:
                String systemName = System.getProperty("os.name").toLowerCase();
                if (systemName.indexOf("win") != -1){//win
                    fsDirectory = SimpleFSDirectory.open(new File(indexPath).toPath(), NoLockFactory.INSTANCE);
                }else {
                    fsDirectory = NIOFSDirectory.open(new File(indexPath).toPath(), NoLockFactory.INSTANCE);
                }
                break;
        }
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
        return indexWriter;
    }

    /**
     * 创建多线程读取索引
     * @param indexWriter
     * @return
     * @throws IOException
     */
    public static IndexSearcher createIndexSearcher(IndexWriter indexWriter) throws IOException {
        DirectoryReader directoryReader = DirectoryReader.open(indexWriter);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader, LuceneX.getExecutorService());
        return indexSearcher;
    }
}
