/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: yuxian.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年11月9日 下午3:06:27
 * @version: V1.0  
 */
package com.ld.lucenex;

import java.util.Date;

import org.ansj.splitWord.analysis.BaseAnalysis;

import com.ld.lucenex.base.CosineSimilarity;

/**
 * @ClassName: yuxian
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年11月9日 下午3:06:27
 */
public class yuxian {
	public static void main(String[] args) {
		BaseAnalysis.parse("1213");
		long time = new Date().getTime();
		double similarity = CosineSimilarity.getSimilarity("alkdm","alkdm aa");
		System.out.println(new Date().getTime()-time);
		System.out.println(similarity);
	}
}
