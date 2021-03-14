package com.ld.lucenex.core;

import com.ld.lucenex.field.LDSort;
import com.ld.lucenex.field.LDType;
import org.apache.lucene.document.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

public class ObjectToDocument extends LdDocument{

    Map<String,LDType> fieldTypes;
    Map<String, LDSort> fieldSorts;
    public ObjectToDocument(){}

    public <T> ObjectToDocument(T t, Map<String,LDType> fieldTypes,Map<String, LDSort> fieldSorts) throws IllegalAccessException {
        createData(t,fieldTypes,fieldSorts);
    }

    public <T> ObjectToDocument(T t, Map<String,LDType> fieldTypes) throws IllegalAccessException {
        createData(t,fieldTypes,null);
    }

    public <T> ObjectToDocument(T t) throws IllegalAccessException {
        createData(t,null,null);
    }

    public <T> void  createData(T t, Map<String,LDType> fieldTypes,Map<String, LDSort> fieldSorts) throws IllegalAccessException {
        this.fieldTypes = fieldTypes;
        this.fieldSorts = fieldSorts;
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field field:declaredFields){
            String name = field.getName();
            field.setAccessible(true);
            Object o = field.get(t);
            LDType ldType = ldType(field);
            if (ldType == LDType.TextField){
                this.add(new TextField(name,o.toString(),org.apache.lucene.document.Field.Store.YES));
            }else if (ldType == LDType.StringField){
                this.add(new StringField(name, o.toString(), org.apache.lucene.document.Field.Store.YES));
            }else if (ldType == LDType.String_TextField) {
                this.add(new StringField(name, o.toString(), org.apache.lucene.document.Field.Store.YES));
                this.add(new TextField(name,o.toString(),org.apache.lucene.document.Field.Store.YES));
            }else if (ldType == LDType.DateField){
                Date time = (Date) o;
                add(new LongPoint(name,time.getTime()));
                add(new StoredField(name,time.getTime()));
            }else if (ldType == LDType.IntPoint){
                this.add(new IntPoint(name,Integer.parseInt(o.toString())));
            }else if (ldType == LDType.LongPoint){
                this.add(new LongPoint(name, Long.valueOf(o.toString())));
            }else if (ldType == LDType.FloatPoint){
                this.add(new FloatPoint(name, Float.valueOf(o.toString())));
            }else if (ldType == LDType.DoublePoint){
                this.add(new DoublePoint(name, Double.valueOf(o.toString())));
            }else if (ldType == LDType.BinaryPoint){
                byte[] bytes = (byte[])o;
                this.add(new BinaryPoint(name,bytes));
                this.add(new StoredField(name,bytes));
            }
        }
    }

    public LDSort lDSort(Field field){
        //有自定义类型
        if (fieldSorts != null && fieldSorts.containsKey(field.getName())){
            return fieldSorts.get(field.getName());
        }
        return LDSort.SortNull;
    }

    public LDType ldType(Field field){
        //有自定义类型
        if (fieldTypes != null && fieldTypes.containsKey(field.getName())){
            return fieldTypes.get(field.getName());
        }
        String typeName = field.getType().getName();
        if (typeName.equals(JavaTypeName.STRING_TYPE_NAME)){
            return LDType.StringField;
        }else if (typeName.equals(JavaTypeName.DOUBLE_TYPE_NAME) || typeName.equals(JavaTypeName.DOUBLE_TYPE_NAME_DOUBLE)){
            return LDType.DoublePoint;
        }else if (typeName.equals(JavaTypeName.FLOAT_TYPE_NAME) || typeName.equals(JavaTypeName.FLOAT_TYPE_NAME_FLOAT)){
            return LDType.FloatPoint;
        }else if (typeName.equals(JavaTypeName.INTEGER_TYPE_NAME) || typeName.equals(JavaTypeName.INT_TYPE_NAME)){
            return LDType.IntPoint;
        }else if (typeName.equals(JavaTypeName.LONG_TYPE_NAME) || typeName.equals(JavaTypeName.LONG_TYPE_NAME_LONG)){
            return LDType.LongPoint;
        }else {
            return LDType.NULL;
        }
    }

}
