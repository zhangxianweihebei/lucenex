//package com.ld.lucenex.core;
//import java.lang.reflect.Field;
//import java.util.*;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.ld.lucenex.base.Const;
//import com.ld.lucenex.field.FieldKey;
//import org.apache.lucene.document.*;
//import org.apache.lucene.index.IndexableField;
//import org.apache.lucene.util.BytesRef;
//
//public final class MyDocument implements Iterable<IndexableField> {
//    private final List<IndexableField> fields = new ArrayList();
//    private static final String[] NO_STRINGS = new String[0];
//
//    public MyDocument(){
//
//    }
//
//    public MyDocument(Object object, List<Field> fields) {
//        add(new IntPoint("lucenex_total", 0));
//        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(object));
//        if (fields.size() == 0){//没有定义字段类型 那么统一 string处理
//            jsonObject.forEach((k,v)-> add(new StringField( k, v.toString(), org.apache.lucene.document.Field.Store.YES)));
//        }else {
//            fields.forEach(e->{
//                FieldKey fieldKey = e.getAnnotation(FieldKey.class);
//                String name = e.getName();
//                boolean containsKey = jsonObject.containsKey(name);
//                if (containsKey){
//                    switch (fieldKey.type()){
//                        case IntPoint:
//                            int intValue = jsonObject.getIntValue(name);
//                            add(new IntPoint(name,intValue));
//                            add(new StoredField(name,intValue));
//                            break;
//                        case LongPoint:
//                            long longValue = jsonObject.getLongValue(name);
//                            add(new LongPoint(name,longValue));
//                            add(new StoredField(name,longValue));
//                            break;
//                        case DateField:
//                            Date date = jsonObject.getDate(name);
//                            long time = date.getTime();
//                            add(new LongPoint(name,time));
//                            add(new StoredField(name,time));
//                            break;
//                        case FloatPoint:
//                            float floatValue = jsonObject.getFloatValue(name);
//                            add(new FloatPoint(name, floatValue));
//                            add(new StoredField(name, floatValue));
//                            break;
//                        case DoublePoint:
//                            double doubleValue = jsonObject.getDoubleValue(name);
//                            add(new DoublePoint( name, doubleValue));
//                            add(new StoredField( name, doubleValue));
//                            break;
//                        case BinaryPoint:
//                            byte[] bytes = jsonObject.getBytes(name);
//                            add(new BinaryPoint( name, bytes));
//                            add(new StoredField( name, bytes));
//                            break;
//                        case StringField:
//                            String string = jsonObject.getString(name);
//                            add(new StringField( name, string, org.apache.lucene.document.Field.Store.YES));
//                            break;
//                        case String_TextField:
//                            String s_t = jsonObject.getString(name);
//                            add(new StringField( String.join("_",name, Const.STRING_TEXTFIELD_STR), s_t, org.apache.lucene.document.Field.Store.YES));
//                            add(new TextField( String.join("_",name, Const.STRING_TEXTFIELD_TEXT), s_t, org.apache.lucene.document.Field.Store.YES));
//                            break;
//                        case TextField:
//                            String t = jsonObject.getString(name);
//                            add(new TextField( name, t, org.apache.lucene.document.Field.Store.YES));
//                            break;
//                        default:
//                            String s = jsonObject.getString(name);
//                            add(new StringField( name, s, org.apache.lucene.document.Field.Store.YES));
//                            break;
//                    }
//                    //排序存储
//                    switch (fieldKey.sort()) {
//                        case SortNull:
//                            break;
//                        case SortedDocValuesField:
//                            add(new SortedDocValuesField(name, new BytesRef(jsonObject.getString(name))));
//                            break;
//                        case SortedSetDocValuesField:
//                            add(new SortedSetDocValuesField(name, new BytesRef(jsonObject.getString(name))));
//                            break;
//                        case NumericDocValuesField:
//                            add(new NumericDocValuesField(name, Long.valueOf(jsonObject.getString(name))));
//                            break;
//                        case SortedNumericDocValuesField:
//                            add(new SortedNumericDocValuesField(name, Long.parseLong(jsonObject.getString(name))));
//                            break;
//                        default:
//                    }
//                }
//            });
//        }
//    }
//
//    public Iterator<IndexableField> iterator() {
//        return this.fields.iterator();
//    }
//
//    public final void add(IndexableField field) {
//        this.fields.add(field);
//    }
//
//    public final void removeField(String name) {
//        Iterator it = this.fields.iterator();
//
//        IndexableField field;
//        do {
//            if (!it.hasNext()) {
//                return;
//            }
//
//            field = (IndexableField)it.next();
//        } while(!field.name().equals(name));
//
//        it.remove();
//    }
//
//    public final void removeFields(String name) {
//        Iterator it = this.fields.iterator();
//
//        while(it.hasNext()) {
//            IndexableField field = (IndexableField)it.next();
//            if (field.name().equals(name)) {
//                it.remove();
//            }
//        }
//
//    }
//
//    public final BytesRef[] getBinaryValues(String name) {
//        List<BytesRef> result = new ArrayList();
//
//        for (IndexableField field : this.fields) {
//            if (field.name().equals(name)) {
//                BytesRef bytes = field.binaryValue();
//                if (bytes != null) {
//                    result.add(bytes);
//                }
//            }
//        }
//
//        return (BytesRef[])result.toArray(new BytesRef[result.size()]);
//    }
//
//    public final BytesRef getBinaryValue(String name) {
//
//        for (IndexableField field : this.fields) {
//            if (field.name().equals(name)) {
//                BytesRef bytes = field.binaryValue();
//                if (bytes != null) {
//                    return bytes;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public final IndexableField getField(String name) {
//        Iterator var2 = this.fields.iterator();
//
//        IndexableField field;
//        do {
//            if (!var2.hasNext()) {
//                return null;
//            }
//
//            field = (IndexableField)var2.next();
//        } while(!field.name().equals(name));
//
//        return field;
//    }
//
//    public IndexableField[] getFields(String name) {
//        List<IndexableField> result = new ArrayList();
//
//        for (IndexableField field : this.fields) {
//            if (field.name().equals(name)) {
//                result.add(field);
//            }
//        }
//
//        return (IndexableField[])result.toArray(new IndexableField[result.size()]);
//    }
//
//    public final List<IndexableField> getFields() {
//        return Collections.unmodifiableList(this.fields);
//    }
//
//    public final String[] getValues(String name) {
//        List<String> result = new ArrayList();
//
//        for (IndexableField field : this.fields) {
//            if (field.name().equals(name) && field.stringValue() != null) {
//                result.add(field.stringValue());
//            }
//        }
//
//        if (result.size() == 0) {
//            return NO_STRINGS;
//        } else {
//            return (String[])result.toArray(new String[result.size()]);
//        }
//    }
//
//    public final String get(String name) {
//        Iterator var2 = this.fields.iterator();
//
//        IndexableField field;
//        do {
//            if (!var2.hasNext()) {
//                return null;
//            }
//
//            field = (IndexableField)var2.next();
//        } while(!field.name().equals(name) || field.stringValue() == null);
//
//        return field.stringValue();
//    }
//
//    public final String toString() {
//        StringBuilder buffer = new StringBuilder();
//        buffer.append("Document<");
//
//        for(int i = 0; i < this.fields.size(); ++i) {
//            IndexableField field = (IndexableField)this.fields.get(i);
//            buffer.append(field.toString());
//            if (i != this.fields.size() - 1) {
//                buffer.append(" ");
//            }
//        }
//
//        buffer.append(">");
//        return buffer.toString();
//    }
//
//    public void clear() {
//        this.fields.clear();
//    }
//}
