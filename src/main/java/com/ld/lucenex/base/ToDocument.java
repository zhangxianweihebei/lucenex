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
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	 */
	public Document getDocument(Object object) {
		Object v;
		if(object instanceof Map) {
			v=new ObjectMapper().convertValue(object, BaseConfig.getConstants().getDefaultClass());
		}else {
			v=object;
		}
		List<Field> fields = ClassKit.getFields(v.getClass());
		return null;
	}

}
