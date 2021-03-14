package com.ld.lucenex.core;

import com.ld.lucenex.field.LDSort;
import com.ld.lucenex.field.LDType;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexableField;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DocumentToObject{

    public DocumentToObject(Document document){
        List<IndexableField> documentFields = document.getFields();
        for (IndexableField field : documentFields) {
            String name = field.name();
            System.out.println(name);
        }
    }

}
