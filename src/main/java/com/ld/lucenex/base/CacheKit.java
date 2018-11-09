/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: CacheKit.java
 * @Prject: route
 * @Package: com.nnthink.route.core
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年10月19日 上午11:35:15
 * @version: V1.0  
 */
package com.ld.lucenex.base;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @ClassName: CacheKit
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年10月19日 上午11:35:15
 */
public class CacheKit {

	private static CacheManager cacheManager = new CacheManager(CacheKit.class.getResourceAsStream("/cache.xml"));

	static Cache serverConfig = cacheManager.getCache("serverConfig");

}
