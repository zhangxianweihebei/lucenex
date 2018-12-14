/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: ToDocument.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午12:04:47
 * @version: V1.0
 */
package com.ld.lucenex.base;

import com.alibaba.fastjson.JSONObject;
import com.ld.lucenex.field.FieldKey;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ToDocument
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午12:04:47
 */
public class ToDocument {

    private static Logger logger = LoggerFactory.getLogger(ToDocument.class);


    /**
     * @param object
     * @param fields
     * @return
     * @throws IllegalAccessException
     * @Title: getDocument
     * @Description: Object 返回一个 Document
     * @return: Document
     */
    public static Document getDocument(Object object,Field[] fields) {
        if(object instanceof Map){
            return map(object,fields);
        }else if(object instanceof JSONObject){
            return fastjson(object,fields);
        }else{
            Document document = new Document();
            document.add(new IntPoint("lucenex_total", 0));
            for (int i = 0, size = fields.length; i < size; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                if (field.isAnnotationPresent(FieldKey.class)) {
                    String fieldName = field.getName();
                    FieldKey fieldKey = field.getAnnotation(FieldKey.class);
                    try {
                        Object fieldValue = field.get(object);
                        add(document,fieldName,fieldValue,fieldKey);
                    } catch (IllegalAccessException e) {
                        logger.error("ToDocument.getDocument error", e);
                    }
                }

            }
            return document;
        }
    }

    public static Document fastjson(Object object, Field[] fields){
        JSONObject map = (JSONObject) object;
        Document document = new Document();
        document.add(new IntPoint("lucenex_total", 0));
        for (int i = 0, size = fields.length; i < size; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(FieldKey.class)) {
                String fieldName = field.getName();
                Object fieldValue = map.get(fieldName);
                FieldKey fieldKey = field.getAnnotation(FieldKey.class);
                add(document,fieldName,fieldValue,fieldKey);
            }

        }
        return document;
    }

    public static Document map(Object object, Field[] fields){
        Map map = (Map) object;
        Document document = new Document();
        document.add(new IntPoint("lucenex_total", 0));
        for (int i = 0, size = fields.length; i < size; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(FieldKey.class)) {
                String fieldName = field.getName();
                Object fieldValue = map.get(fieldName);
                FieldKey fieldKey = field.getAnnotation(FieldKey.class);
                add(document,fieldName,fieldValue,fieldKey);
            }

        }
        return document;
    }

    /**
     * 获取一个 List<Document>
     *
     * @param object
     * @param clas
     * @param <T>
     * @return
     */
    public static <T> List<Document> getDocuments(List<T> object, Class<?> clas) {
        List<Document> dataList = new ArrayList<>(object.size());
        Field[] fields = FieldUtils.getAllFields(clas);
        for (int i = 0, size = object.size(); i < size; i++) {
            dataList.add(getDocument(object.get(i), fields));
        }
        return dataList;
    }

    private static void add(Document doc, String fieldName, Object fieldValue, FieldKey fieldKey){
        String value = "";
        if (fieldValue != null) {
            value = fieldValue.toString();
        }
        //字段存储
        switch (fieldKey.type()) {
            case IntPoint:
                int parseInt = Integer.parseInt(value);
                doc.add(new IntPoint(fieldName, parseInt));
                doc.add(new StoredField(fieldName, parseInt));
                break;
            case LongPoint:
                Long valueOf = Long.valueOf(value);
                doc.add(new LongPoint(fieldName, valueOf));
                doc.add(new StoredField(fieldName, valueOf));
                break;
            case DateField:
                long date = ((Date) fieldValue).getTime();
                doc.add(new LongPoint(fieldName, date));
                doc.add(new StoredField(fieldName, date));
                break;
            case FloatPoint:
                Float valueOf2 = Float.valueOf(value);
                doc.add(new FloatPoint(fieldName, valueOf2));
                doc.add(new StoredField(fieldName, valueOf2));
                break;
            case DoublePoint:
                Double valueOf3 = Double.valueOf(value);
                doc.add(new DoublePoint(fieldName, valueOf3));
                doc.add(new StoredField(fieldName, valueOf3));
                break;
            case BinaryPoint:
                byte[] bytes = value.getBytes();
                doc.add(new BinaryPoint(fieldName, bytes));
                doc.add(new StoredField(fieldName, bytes));
                break;
            case StringField:
                doc.add(new StringField(fieldName, value, org.apache.lucene.document.Field.Store.YES));
                break;
            case String_TextField:
                doc.add(new StringField(fieldName + "_str", value, org.apache.lucene.document.Field.Store.YES));
                doc.add(new TextField(fieldName + "_txt", value, org.apache.lucene.document.Field.Store.YES));
                break;
            case TextField:
                doc.add(new TextField(fieldName, value, org.apache.lucene.document.Field.Store.YES));
                break;
        }

        //排序存储
        switch (fieldKey.sort()) {
            case SortNull:
                break;
            case SortedDocValuesField:
                doc.add(new SortedDocValuesField(fieldName, new BytesRef(value)));
                break;
            case SortedSetDocValuesField:
                doc.add(new SortedSetDocValuesField(fieldName, new BytesRef(value)));
                break;
            case NumericDocValuesField:
                doc.add(new NumericDocValuesField(fieldName, Long.valueOf(value)));
                break;
            case SortedNumericDocValuesField:
                doc.add(new SortedNumericDocValuesField(fieldName, Long.valueOf(value)));
                break;
        }
    }
}