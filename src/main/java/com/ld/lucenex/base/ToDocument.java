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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.lucenex.field.FieldKey;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.util.BytesRef;

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

    /**
     * @Title: getDocument
     * @Description: Object 返回一个 Document
     * @param object
     * @param fields
     * @return
     * @return: Document
     * @throws IllegalAccessException
     */
    public static Document getDocument(Object object, Class<?> clas, Field[] fields) {
        Object o = toObject(object, clas);
        Document document = new Document();
        document.add(new IntPoint("lucenex_total", 0));
        for (int i = 0, size = fields.length; i < size; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(FieldKey.class)) {
                try {
                    add(document, field, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return document;
    }

    public static <T> List<Document> getDocuments(List<T> object, Class<?> clas) {
        List<Document> dataList = new ArrayList<>(object.size());
        Field[] fields = FieldUtils.getAllFields(clas);
        for (int i = 0, size = object.size(); i < size; i++) {
            T t = object.get(i);
            dataList.add(getDocument(t, clas, fields));
            t = null;
        }
        return dataList;
    }

    public static Object toObject(Object object, Class<?> clas) {
        if (object instanceof Object) {
            return object;
        } else if (object instanceof Map) {
            return new ObjectMapper().convertValue(object, clas);
        } else {
            return object;
        }
    }

    private static void add(Document doc, Field field, Object obj) throws IllegalAccessException {
        String value;
        Object object = field.get(obj);
        if (object != null) {
            value = object.toString();
        } else {
            return;
        }
        FieldKey fieldKey = field.getAnnotation(FieldKey.class);
        String name = field.getName();


        //字段存储
        switch (fieldKey.type()) {
            case IntPoint:
                int parseInt = Integer.parseInt(value);
                doc.add(new IntPoint(name, parseInt));
                doc.add(new StoredField(name, parseInt));
                break;
            case LongPoint:
                Long valueOf = Long.valueOf(value);
                doc.add(new LongPoint(name, valueOf));
                doc.add(new StoredField(name, valueOf));
                break;
            case DateField:
                long date = ((Date) field.get(obj)).getTime();
                doc.add(new LongPoint(name, date));
                doc.add(new StoredField(name, date));
                break;
            case FloatPoint:
                Float valueOf2 = Float.valueOf(value);
                doc.add(new FloatPoint(name, valueOf2));
                doc.add(new StoredField(name, valueOf2));
                break;
            case DoublePoint:
                Double valueOf3 = Double.valueOf(value);
                doc.add(new DoublePoint(name, valueOf3));
                doc.add(new StoredField(name, valueOf3));
                break;
            case BinaryPoint:
                byte[] bytes = value.getBytes();
                doc.add(new BinaryPoint(name, bytes));
                doc.add(new StoredField(name, bytes));
                break;
            case StringField:
                doc.add(new StringField(name, value, org.apache.lucene.document.Field.Store.YES));
                break;
            case String_TextField:
                doc.add(new StringField(name + "_str", value, org.apache.lucene.document.Field.Store.YES));
                doc.add(new TextField(name + "_txt", value, org.apache.lucene.document.Field.Store.YES));
                break;
            case TextField:
                doc.add(new TextField(name, value, org.apache.lucene.document.Field.Store.YES));
                break;
        }

        //排序存储
        switch (fieldKey.sort()) {
            case SortNull:
                break;
            case SortedDocValuesField:
                doc.add(new SortedDocValuesField(name, new BytesRef(value)));
                break;
            case SortedSetDocValuesField:
                doc.add(new SortedSetDocValuesField(name, new BytesRef(value)));
                break;
            case NumericDocValuesField:
                doc.add(new NumericDocValuesField(name, Long.valueOf(value)));
                break;
            case SortedNumericDocValuesField:
                doc.add(new SortedNumericDocValuesField(name, Long.valueOf(value)));
                break;
        }
    }
}