package com.ld.lucenex.util;

import com.alibaba.fastjson.JSONObject;
import com.ld.lucenex.core.LdDocument;
import com.ld.lucenex.core.ObjectToDocument;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentUtil {

    public static <T> LdDocument toDocument(T t) throws IllegalAccessException {
        ObjectToDocument indexableFields = new ObjectToDocument(t);
        return indexableFields;
    }

    public static <T> List<LdDocument> toDocuments(List<T> ts) throws IllegalAccessException {
        List<LdDocument> documents = new ArrayList<>(ts.size());
        for (T t : ts){
            documents.add(toDocument(t));
        }
        return documents;
    }

    public static <T> T toObject(Document doc,Class<T> clazz){
        List<IndexableField> fields = doc.getFields();
        JSONObject jsonObject = new JSONObject(fields.size(),true);
        for (IndexableField field : fields) {
            jsonObject.put(field.name(), field.stringValue());
        }
        return jsonObject.toJavaObject(clazz);
    }

    public static Map<String, Object> toMap(Document doc){
        List<IndexableField> fields = doc.getFields();
        Map<String, Object> map = new HashMap<>(fields.size());
        for (IndexableField field : fields) {
            map.put(field.name(), field.stringValue());
        }
        return map;
    }

    public static List<Map<String,Object>> toMaps(Document[] documents){
        List<Map<String,Object>> maps = new ArrayList<>(documents.length);
        for (Document document:documents){
            Map<String, Object> stringObjectMap = toMap(document);
            maps.add(stringObjectMap);
        }
        return maps;
    }

    public static List<JSONObject> toJsons(Document[] documents){
        List<JSONObject> jsonObjects = new ArrayList<>(documents.length);
        for (Document document : documents) {
            JSONObject jsonObject = toJSON(document);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    public static JSONObject toJSON(Document doc){
        List<IndexableField> fields = doc.getFields();
        JSONObject jsonObject = new JSONObject(fields.size(),true);
        for (IndexableField field : fields) {
            jsonObject.put(field.name(), field.stringValue());
        }
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
