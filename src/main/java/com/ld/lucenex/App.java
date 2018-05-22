/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: App.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 上午11:00:57
 * @version: V1.0  
 */
package com.ld.lucenex;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: App
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 上午11:00:57
 */
public class App {
	
	/**
	 * @Title: main
	 * @Description: TODO
	 * @param args
	 * @return: void
	 */
	public static void main(String[] args) {
		System.out.println("世界你好!");
		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
		map.put("a", "1213");
		map.put("b", "1213");
		map.put("c", "1213");
		map.put("d", "1213");
		map.putIfAbsent("d","dsd");
//		map.forEach((k,v)->System.out.println(k+v));
//		System.out.println(String.join("->", "a","b","c","d"));
//		System.out.println(Integer.MIN_VALUE - 1);
		List<String> myList =
			    Arrays.asList("a1", "a2", "b1", "c2", "c1");
		List<String> collect = myList
		.stream()
//		.filter(s->s.startsWith("c"))
		.filter((v)->{
			
			if(v.equals("a1")) {
				return false;
			}
			return true;
		})
		.map(e->xxx(e))
		.collect(Collectors.toList());
		System.out.println(collect);
		String join = String.join("->", "a","b","c","d","e->l");
		String[] split = join.split("->");
		String v ="";
		for (String string : split) {
			v+=string;
		}
		System.out.println(v);
	}
	
	public static String xxx(String x) {
		return "======"+x;
	}

}
