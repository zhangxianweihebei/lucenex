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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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
		List<DemoDto> collect = myList
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
//		System.out.println(collect);
//		String join = String.join(",", "a","b","c","d","e,l");
//		List<String> asList = Arrays.asList(join);
//		System.out.println(asList+"---->"+join.split("->").length);
//		
//		
//		String[] split = join.split("->");
//		String v ="";
//		for (String string : split) {
//			v+=string;
//		}
//		System.out.println(v);
		List<String> p = Arrays.asList("f","=");
		List<String> l = Arrays.asList("f","=","new","File","(","<BREAK>","Environment",".","getExternalStorageDirectory","(","<BREAK>",")","<BREAK>","+","<str>",")",";","<BREAK>","<ENTER>","if","(","<BREAK>","!","f",".","exists","(","<BREAK>",")",")","{","<BREAK>","<ENTER>","<IND>");
		int j=0;
		List<String> collect2 = l.stream().skip(1)
				.collect(Collectors.toList());
		System.out.println(collect2);
//		DemoService duang = LdDuang.duang(new DemoService(""));
//		duang.add();
	}
	public static String x(List<String> l,int i,String k) {
		if(i ==l.size()-1 || l.get(i).equals("<BREAK>")) {
			return k;
		}else {
			return x(l, i+1, k+l.get(i));
		}
	}
	
	public static DemoDto xxx(String x) {
		return new DemoDto(x);
	}

}
class DemoDto{
	private String name;
	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @Title:DemoDto
	 * @Description:TODO
	 */
	public DemoDto(String name) {
		this.name = name;
	}
	/* (non Javadoc)
	 * @Title: toString
	 * @Description: TODO
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DemoDto [name=" + name + "]";
	}
	
}
