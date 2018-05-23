/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: Service.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.core
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:46:22
 * @version: V1.0  
 */
package com.ld.lucenex.core;

import com.ld.lucenex.config.SourceConfig;

/**
 * @ClassName: Service
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月23日 下午6:46:22
 */
public class Service {
	
	public SourceConfig config;
	
	/**
	 * @Title:Service
	 * @Description:TODO
	 */
	public Service() {
		// TODO 自动生成的构造函数存根
	}
	/**
	 * @Title:Service
	 * @Description:TODO
	 */
	public Service(String dataKey) {
		ManySource.putContextHolder(dataKey);
		config = ManySource.getDataSource();
	}

}
