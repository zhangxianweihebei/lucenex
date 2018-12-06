package com.ld.lucenex.core;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneX {

    private static Logger logger = LoggerFactory.getLogger(LuceneX.class);

    /**
     * 启动 lucenex
     *
     * @param clas
     */
    public static void start(Class<?> clas) {
        try {
            LuceneXConfig config = (LuceneXConfig) clas.newInstance();
            BaseConfig.configLuceneX(config);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("lucenex start error", e);
        }
    }
}
