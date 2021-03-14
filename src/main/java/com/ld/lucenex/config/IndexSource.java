/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: LucenexConfig.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.config
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:36:15
 * @version: V1.0
 */
package com.ld.lucenex.config;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.ListeningExecutorService;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.util.CommonUtil;

import lombok.Data;

/**
 * @ClassName: LucenexConfig
 * @Description: TODO
 * @author: Myzhang
 * @param <T>
 * @date: 2018年5月22日 下午6:36:15
 */
@Data
public class IndexSource{

	private String indexPath;
	private IndexWriter indexWriter;
	private IndexSearcher indexSearcher;
	private PerFieldAnalyzerWrapper perFieldAnalyzerWrapper;

	private Logger logger = LoggerFactory.getLogger(IndexSource.class);

	IndexSource( String indexPath,
			IndexWriter indexWriter,
			IndexSearcher indexSearcher,
			PerFieldAnalyzerWrapper perFieldAnalyzerWrapper){
		this.indexPath = indexPath;
		this.indexWriter = indexWriter;
		this.indexSearcher = indexSearcher;
		this.perFieldAnalyzerWrapper = perFieldAnalyzerWrapper;
	}
	
	public static IndexSource.IndexSourceBuilder builder(){
		return new IndexSource.IndexSourceBuilder();
	}

	public static class IndexSourceBuilder{
		private String indexPath;
		private IndexWriter indexWriter;
		private IndexSearcher indexSearcher;
		private PerFieldAnalyzerWrapper perFieldAnalyzerWrapper;
		IndexSourceBuilder(){}
		
		public IndexSource.IndexSourceBuilder indexPath(String indexPath) {
			this.indexPath = indexPath;
			return this;
		}
		public IndexSource.IndexSourceBuilder indexWriter(IndexWriter indexWriter) {
			this.indexWriter = indexWriter;
			return this;
		}
		public IndexSource.IndexSourceBuilder indexSearcher(IndexSearcher indexSearcher) {
			this.indexSearcher = indexSearcher;
			return this;
		}
		public IndexSource.IndexSourceBuilder perFieldAnalyzerWrapper(PerFieldAnalyzerWrapper perFieldAnalyzerWrapper) {
			this.perFieldAnalyzerWrapper = perFieldAnalyzerWrapper;
			return this;
		}
		
		public IndexSource build() throws IOException {
			File file = new File(this.indexPath);
			if (!file.exists()) {
				boolean mkdirs = file.mkdirs();
				if (!mkdirs) {
					throw new IOException("Unable to create index directory");
				}
			}
			if (this.perFieldAnalyzerWrapper == null) {
				this.perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer());
			}
			if (this.indexWriter == null) {
				this.indexWriter = CommonUtil.createIndexWriter(this.indexPath, this.perFieldAnalyzerWrapper);
			}
			if (this.indexSearcher == null) {
				this.indexSearcher = CommonUtil.createIndexSearcher(this.indexWriter);
			}
			return new IndexSource(this.indexPath,this.indexWriter,this.indexSearcher,this.perFieldAnalyzerWrapper);
		}
	}

	/**
	 * 更新索引库
	 */
	public void updateIndexSource(){
		synchronized (this.indexSearcher){
			try {
				//进实时转换
				IndexSearcher indexSearcher = CommonUtil.createIndexSearcher(this.indexWriter);
				this.indexSearcher = indexSearcher;
				logger.info("updateIndexSource ok");
			} catch (IOException e) {
				logger.error("Near Real-Time updateIndexSource error",e);
			}finally {
				ListeningExecutorService executorService = LuceneX.getInstance().getExecutorService();
				ListenableFuture<Void> listenableFuture = executorService.submit(() -> {
					try {
						indexWriter.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						indexWriter.commit();
					} catch (IOException e) {
						e.printStackTrace();
					}
					logger.info("source commit ...");
					return null;
				});
				try {
					Void aVoid = listenableFuture.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				Futures.addCallback(listenableFuture, new FutureCallback<Void>() {
					@Override
					public void onSuccess(@Nullable Void aVoid) {
						logger.info("source commit success");
					}
					@Override
					public void onFailure(Throwable throwable) {
						logger.error("{} - source commit error",indexPath,throwable);
					}
				},executorService);
			}
		}
	}
}
