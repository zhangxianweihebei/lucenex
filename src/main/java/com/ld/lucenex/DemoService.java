/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: DemoService.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:57:32
 * @version: V1.0  
 */
package com.ld.lucenex;

import com.ld.lucenex.core.Service;

/**
 * @ClassName: DemoService
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:57:32
 */
public class DemoService extends Service{

	/**
	 * @Title:DemoService
	 * @Description:TODO
	 * @param dataKey
	 */
	public DemoService(String dataKey) {
		super(dataKey);
	}
	/**
	 * @Title:DemoService
	 * @Description:TODO
	 */
	public DemoService() {
		// TODO 自动生成的构造函数存根
	}
	public void add() {
		System.out.println("添加");
	}

}
