package com.ld.lucenex.core;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.base.Const;
import com.ld.lucenex.config.IndexSource;
import com.ld.lucenex.config.LuceneXConfig;
import org.apache.lucene.index.IndexWriter;
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
    static int cpuNum = Runtime.getRuntime().availableProcessors();

    public LuceneX(){
        initExecutorService(cpuNum*2);
    }
    public LuceneX(LuceneXConfig luceneXConfig){
        BaseConfig.configLuceneX(luceneXConfig);
        initExecutorService(cpuNum*2);
    }
    public LuceneX(LuceneXConfig luceneXConfig,int threadNum){
        BaseConfig.configLuceneX(luceneXConfig);
        initExecutorService(threadNum);
    }

    public LuceneX(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        LuceneXConfig luceneXConfig = (LuceneXConfig)clazz.newInstance();
        BaseConfig.configLuceneX(luceneXConfig);
        initExecutorService(cpuNum*2);
    }
    public LuceneX(Class<?> clazz,int threadNum) throws IllegalAccessException, InstantiationException {
        LuceneXConfig luceneXConfig = (LuceneXConfig)clazz.newInstance();
        BaseConfig.configLuceneX(luceneXConfig);
        initExecutorService(threadNum);
    }

    private void initExecutorService(int numThread){
        System.out.println("initExecutorService");
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numThread));
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.warn("lucenex close ...");
                try {
                    close();
                    logger.warn("lucenex close success");
                } catch (IOException e) {
                    logger.error("lucenex close error",e);

                }
            }
        });
    }


    public static ListenableFuture submit(Runnable runnable){
        return executorService.submit(runnable);
    }

    public static ListeningExecutorService getExecutorService(){
        return executorService;
    }

    public static void addIndexSource(String key,IndexSource indexSource){
        synchronized (sourceMap){
            sourceMap.put(key,indexSource);
        }
    }

    public static IndexSource getIndexSource(String key){
        if (sourceMap.size() == 0 || key == null){
            return null;
        }
        //如果key是默认值 那么 获取第一个
        if (Const.DEFAULT_SERVICE_KEY.equals(key)){
            String next = sourceMap.keySet().iterator().next();
            return sourceMap.get(next);
        }else {
            return sourceMap.get(key);
        }
    }


    @Override
    public void close() throws IOException {
        //延迟3秒关闭，防止有数据没提交进来
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            logger.error("executorService error",e);
        }
        sourceMap.forEach((k,v)->{
            IndexWriter indexWriter = v.getIndexWriter();
            try {
                indexWriter.flush();
                indexWriter.commit();
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sourceMap.clear();
    }
}
