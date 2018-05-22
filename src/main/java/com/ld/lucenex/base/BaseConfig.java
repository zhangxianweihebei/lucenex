/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: BaseConfig.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午12:30:22
 * @version: V1.0  
 */
package com.ld.lucenex.base;

import com.ld.lucenex.config.Constants;

/**
 * @ClassName: BaseConfig
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午12:30:22
 */
public class BaseConfig {
	private static final Constants constants = new Constants();
	
	public static Constants getConstants() {
		return constants;
	}
}
