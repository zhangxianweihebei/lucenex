//package com.ld.lucenex.core;
//
//import java.io.IOException;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.ld.lucenex.config.SourceConfig;
//
///**
// * 多库管理类
// */
//public class ManySource {
//
//    private static Logger logger = LoggerFactory.getLogger(ManySource.class);
//
//    /**
//     * 线程变量
//     */
//    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
//
//    /**
//     * 库的Hash表
//     */
//    private static volatile ConcurrentHashMap<String, SourceConfig> dataSource = new ConcurrentHashMap<>();
//
//    /**
//     * 添加一个库
//     *
//     * @param k
//     * @param v
//     */
//    public static void putDataSource(String k, SourceConfig v) {
//        dataSource.put(k, v);
//    }
//
//    /**
//     * 添加一个线程值
//     *
//     * @param key
//     */
//    public static void setKey(String key) {
//        threadLocal.set(key);
//    }
//
//    public static String get(){
//        return threadLocal.get();
//    }
//
//    /**
//     * 获取一个制定名字的库
//     *
//     * @param k
//     * @return
//     */
//    public static SourceConfig getDataSource(String k) {
//        return dataSource.get(k);
//    }
//
//    /**
//     * 尝试获取一个线程库、如果没有那么获取默认库（第一个库）
//     *
//     * @return
//     */
//    public static SourceConfig getDataSource() {
//        SourceConfig sourceConfig = null;
//        String key = threadLocal.get();
//        if (key != null) {
//            sourceConfig = getDataSource(key);
//        } else {
//            Set<Entry<String, SourceConfig>> entrySet = dataSource.entrySet();
//            for (Entry<String, SourceConfig> entry : entrySet) {
//                sourceConfig = entry.getValue();
//                break;
//            }
//        }
//        return sourceConfig;
//    }
//
//    /**
//     * @Title: submit
//     * @Description: 提交所有库
//     * @return: void
//     */
//    public static void submit() {
//        dataSource.forEach((k, v) -> {
//            logger.info("提交<{}>数据源",k);
//            try {
//                v.getWriter().commit();
//                v.restartReader();
//            } catch (Exception e) {
//                logger.error("提交<{}>数据源",k, e);
//            }
//        });
//    }
//
//    /**
//     * @Title: close
//     * @Description: 关闭所有库
//     * @return: void
//     */
//    public static void close() {
//        submit();
//        dataSource.forEach((k, v) -> {
//            try {
//                v.getWriter().close();
//            } catch (IOException e) {
//            }
//        });
//    }
//
//}
