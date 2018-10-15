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

	private boolean devMode = Const.DEFAULT_DEV_MODE;//开发&上线

	private Class<?> defaultClass;//默认Class


	private boolean isAsynchronous = Const.DEFAULT_ASYNCHRONOUS;//同步异步

	private String defaultDisk;//默认磁盘
	
	
	private boolean highlight = Const.DEFAULT_HIGHLIGHT;//是否高亮
	private String[] htmlFormatter = Const.DEFAULT_HTMLFORMATTER;//高亮标签
	private int HighlightNum = Const.DEFAULT_HighlightNum;//高亮截取数量
	
	private int delayedSyn = 0;//延时同步时间
	

	public int getDelayedSyn() {
		return delayedSyn;
	}
	
	/**
	 * 设置延时同步时间
	 * @param delayedSyn
	 */
	public void setDelayedSyn(int delayedSyn) {
		this.delayedSyn = delayedSyn;
	}
	/**
	 * @fieldName: extDictPath
	 * @fieldType: String
	 * @Description: 公共启用词库目录
	 */
	private String extDictPath;

	/**
	 * @fieldName: extStopwordPath
	 * @fieldType: String
	 * @Description: 公共停用词库目录
	 */
	private String extStopwordPath;

	/**
	 * @param devMode 要设置的 devMode
	 */
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	
	/**
	 * @param highlightNum 要设置的 highlightNum
	 */
	public void setHighlightNum(int highlightNum) {
		HighlightNum = highlightNum;
	}
	/**
	 * @return highlightNum
	 */
	public int getHighlightNum() {
		return HighlightNum;
	}
	/**
	 * @param htmlFormatter 要设置的 htmlFormatter
	 */
	public void setHtmlFormatter(String prefix,String suffix) {
		this.htmlFormatter = new String[] {prefix,suffix};
	}
	/**
	 * @return htmlFormatter
	 */
	public String[] getHtmlFormatter() {
		return htmlFormatter;
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

	/**
	 * @param extDictPath 要设置的 extDictPath
	 */
	public void setExtDictPath(String extDictPath) {
		this.extDictPath = extDictPath;
	}
	/**
	 * @param extStopwordPath 要设置的 extStopwordPath
	 */
	public void setExtStopwordPath(String extStopwordPath) {
		this.extStopwordPath = extStopwordPath;
	}
	/**
	 * @return extDictPath
	 */
	public String getExtDictPath() {
		return extDictPath;
	}
	/**
	 * @return extStopwordPath
	 */
	public String getExtStopwordPath() {
		return extStopwordPath;
	}

	/**
	 * @return isAsynchronous
	 */
	public boolean isAsynchronous() {
		return isAsynchronous;
	}
	/**
	 * 设置异步 同步数据范围《添加，删除，修改》
	 * @param isAsynchronous 要设置的 isAsynchronous
	 */
	public void setAsynchronous(boolean isAsynchronous) {
		this.isAsynchronous = isAsynchronous;
	}
}
