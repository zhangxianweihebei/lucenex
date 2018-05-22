/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: Constants.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.config
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午6:20:11
 * @version: V1.0  
 */
package com.ld.lucenex.config;

import com.ld.lucenex.base.Const;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午6:20:11
 */
public final class Constants {
	
	private boolean devMode = Const.DEFAULT_DEV_MODE;
	
	private Class<?> defaultClass;
	
	private boolean highlight = Const.DEFAULT_HIGHLIGHT;
	
	private String defaultDisk;
	
	/**
	 * @param devMode 要设置的 devMode
	 */
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	
	/**
	 * @return devMode
	 */
	public boolean isDevMode() {
		return devMode;
	}
	
	/**
	 * @param defaultClass 要设置的 defaultClass
	 */
	public void setDefaultClass(Class<?> defaultClass) {
		this.defaultClass = defaultClass;
	}
	
	/**
	 * @return defaultClass
	 */
	public Class<?> getDefaultClass() {
		return defaultClass;
	}
	/**
	 * @param highlight 要设置的 highlight
	 */
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	/**
	 * @return highlight
	 */
	public boolean isHighlight() {
		return highlight;
	}

	/**
	 * @param defaultDisk 要设置的 defaultDisk
	 */
	public void setDefaultDisk(String defaultDisk) {
		this.defaultDisk = defaultDisk;
	}
	/**
	 * @return defaultDisk
	 */
	public String getDefaultDisk() {
		return defaultDisk;
	}
}
