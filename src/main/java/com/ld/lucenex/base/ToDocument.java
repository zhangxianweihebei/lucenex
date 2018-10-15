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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.BinaryPoint;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.SortedSetDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.util.BytesRef;
import org.nlpcn.commons.lang.pinyin.Pinyin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.lucenex.field.FieldKey;

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
	 * @return
	 * @return: Document
	 * @throws IllegalAccessException 
	 */
	public static Document getDocument(Object object,Class<?> clas){
		Object o = toObject(object,clas);
		if(o == null) {
			return null;
		}
		Field[] fields = clas.getDeclaredFields();
		Document document = new Document();
		document.add(new IntPoint("lucenex_total",0));
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if(field.isAnnotationPresent(FieldKey.class)) {
				try {
					add(document, field, o);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
		}
		return document;
	}
	
	public static <T> List<Document> getDocuments(List<T> object,Class<?> clas){
		List<Document> dataList = new ArrayList<>(object.size());
		for (int i = 0,size = object.size(); i < size; i++) {
			dataList.add(getDocument(object.get(i), clas));
		}
		object.clear();
		object = null;
		return dataList;
	}
	
	public static Object toObject(Object object, Class<?> clas) {
		if(object == null) {
			return null;
		}
		Object v = null;
		if(object instanceof Map) {
			v=new ObjectMapper().convertValue(object, clas);
		}else {
			v=object;
		}
		return v;
	}
	
	private static void add(Document doc,Field field,Object obj) throws IllegalAccessException {
		String value;
		Object object = field.get(obj);
		if(object != null) {
			value = object.toString();
		}else {
			return;
		}
		FieldKey fieldKey = field.getAnnotation(FieldKey.class);
		String name = field.getName();


		//字段存储
		switch (fieldKey.type()) {
		case IntPoint:
			int parseInt = Integer.parseInt(value);
			doc.add(new IntPoint(name,parseInt));
			doc.add(new StoredField(name,parseInt));
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
			doc.add(new StringField(name, value,org.apache.lucene.document. Field.Store.YES));
			break;
		case String_TextField:
			doc.add(new StringField(name+"_str", value,org.apache.lucene.document. Field.Store.YES));
			doc.add(new TextField(name+"_txt", value, org.apache.lucene.document.Field.Store.YES));
			if(fieldKey.pinyin()) {
				doc.add(new TextField(name+"_pin", pinyin(value), org.apache.lucene.document.Field.Store.YES));
			}
			break;
		case TextField:
			doc.add(new TextField(name, value, org.apache.lucene.document.Field.Store.YES));
			if(fieldKey.pinyin()) {
				doc.add(new TextField(name+"_pin", pinyin(value), org.apache.lucene.document.Field.Store.YES));
			}
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

	/**
	 * 中文转拼音操作 (支持挺好)
	 * @param v
	 * @return
	 */
	private static String pinyin(String v) {
		List<String> pinyin = Pinyin.pinyin(v);
		StringBuilder sb = new StringBuilder();
		for (int i = 0,size = pinyin.size(); i < size; i++) {
			sb.append(pinyin.get(i)).append(" ");
		}
		return sb.toString();
	}

}
