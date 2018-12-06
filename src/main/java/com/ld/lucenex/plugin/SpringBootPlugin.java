/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: SpringBootPlugin.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.plugin
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月28日 下午2:40:58
 * @version: V1.0
 */
package com.ld.lucenex.plugin;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;

/**
 * @ClassName: SpringBootPlugin
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月28日 下午2:40:58
 */
public class SpringBootPlugin {
    public void add(LuceneXConfig config) {
        BaseConfig.configLuceneX(config);
    }
}
