package com.ld.lucenex.core;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.config.LuceneXConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LuceneX implements Closeable {

    private static Logger logger = LoggerFactory.getLogger(LuceneX.class);

    static ListeningExecutorService executorService;

    static Map<String, IndexSource> sourceMap = new HashMap<>();

    public LuceneX(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        int nThreads = Runtime.getRuntime().availableProcessors();
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(nThreads * 2));
        LuceneXConfig config = (LuceneXConfig) clazz.newInstance();
        BaseConfig.configLuceneX(config);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.warn("lucenex close ...");
                executorService.shutdown();
                try {
                    executorService.awaitTermination(1, TimeUnit.HOURS);
                } catch (InterruptedException e) {
                    logger.error("executorService error",e);
                }
                sourceMap.clear();
                logger.warn("lucenex close success");

            }
        });
    }
    public LuceneX(Class<?> clazz,int nThreads) throws IllegalAccessException, InstantiationException {
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(nThreads));
        LuceneXConfig config = (LuceneXConfig) clazz.newInstance();
        BaseConfig.configLuceneX(config);
    }

    public void run(Runnable runnable){
        executorService.submit(runnable);
    }

    public static ListeningExecutorService getExecutorService(){
        return executorService;
    }

    public static void addIndexSource(String key,IndexSource indexSource){
        sourceMap.put(key,indexSource);
    }

    public static IndexSource getIndexSource(String key){
        IndexSource indexSource = sourceMap.get(key);
        return indexSource;
    }

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

    @Override
    public void close() throws IOException {

    }
//
//    /**
//     * 提交所有
//     */
//    public static void submitAll(){
//        ManySource.submit();
//    }
//
//    /**
//     * 关闭所有
//     */
//    public static void closeAll(){
//        ManySource.close();
//    }
}
