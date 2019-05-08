package com.ld.lucenex.plugin;

import com.jfinal.plugin.IPlugin;
import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.ManySource;

/**
 *
 * jfinal 插件
 */
public class JfinalPlugin implements IPlugin {

    LuceneXConfig config;

    public JfinalPlugin(LuceneXConfig config){
        System.out.println("jksjdksd");
        this.config = config;
    }


    @Override
    public boolean start() {
        BaseConfig.configLuceneX(config);
        return true;
    }

    @Override
    public boolean stop() {
        ManySource.close();
        return true;
    }
}
