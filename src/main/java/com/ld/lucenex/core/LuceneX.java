package com.ld.lucenex.core;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ld.lucenex.config.IndexSource;
import lombok.Getter;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LuceneX{

	private static final Logger logger = LoggerFactory.getLogger(LuceneX.class);

	private static final LuceneX luceneX = new LuceneX();

	private static final String DEFAULT_SERVICE_KEY = "DEFAULT_SERVICE_KEY";

	@Getter
	ListeningExecutorService executorService;

	final Map<String, IndexSource> sourceMap;

	static final int numThread = 2;

	private LuceneX() {
		sourceMap = new ConcurrentHashMap<>(5);
		initExecutorService();
	}

	public static LuceneX getInstance() {
		return LuceneX.luceneX;
	}



	private void initExecutorService(){
		ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(LuceneX.numThread);
		executorService.setKeepAliveTime(3,TimeUnit.SECONDS);
		this.executorService = MoreExecutors.listeningDecorator(executorService);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.warn("lucenex close ...");
			close();
		}));
	}


	public void addIndexSource(String key,IndexSource indexSource){
		synchronized (sourceMap){
			sourceMap.put(key,indexSource);
		}
	}

	public void addIndexSource(IndexSource indexSource){
		File file = new File(indexSource.getIndexPath());
		addIndexSource(file.getName(), indexSource);
	}

	public IndexSource getDefaultIndexSource() {
		return getIndexSource(DEFAULT_SERVICE_KEY);
	}

	public void syncIndexSource(IndexWriter indexWriter) {
		Set<Map.Entry<String, IndexSource>> entries = sourceMap.entrySet();
		for (Map.Entry<String, IndexSource> nextEntry : entries){
			IndexSource nextValue = nextEntry.getValue();
			IndexWriter indexWriter1 = nextValue.getIndexWriter();
			if (indexWriter1 == indexWriter){
                nextValue.updateIndexSource();
                break;
            }
		}
	}

	public IndexSource getIndexSource(String key){
		if (sourceMap.isEmpty()){
			return null;
		}
		//如果key是默认值 那么 获取第一个
		if (DEFAULT_SERVICE_KEY.equals(key)){
			String next = sourceMap.keySet().iterator().next();
			return sourceMap.get(next);
		}else {
			return sourceMap.get(key);
		}
	}


	public void close() {
		sourceMap.forEach((k,v)->{
			IndexWriter indexWriter = v.getIndexWriter();
			try {
				indexWriter.flush();
				indexWriter.commit();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		sourceMap.clear();
	}
}
