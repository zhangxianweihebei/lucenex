/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: ClassKit.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午12:23:42
 * @version: V1.0  
 */
package com.ld.lucenex.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.ld.lucenex.field.FieldKey;

/**
 * @ClassName: ClassKit
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午12:23:42
 */
public class ClassKit {
	
	public static List<Field> getFields(Class<?> clas) {
		List<Field> list = new ArrayList<>();
		Field[] fields = clas.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if(field.isAnnotationPresent(FieldKey.class)) {
				list.add(field);
			}
		}
		return list;
	}

}
