/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: BaseConfig.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午12:30:22
 * @version: V1.0  
 */
package com.ld.lucenex.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.NoLockFactory;

import com.ld.lucenex.analyzer.Dic;
import com.ld.lucenex.analyzer.lucene.LDAnalyzer;
import com.ld.lucenex.config.Constants;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.core.ManySource;

/**
 * @ClassName: BaseConfig
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月22日 下午12:30:22
 */
public class BaseConfig implements InitConfig{

	private static final Constants constants = new Constants();
	private static final BaseConfig baseConfig = new BaseConfig();

	public static void configLuceneX(LuceneXConfig config) {
		config.configConstant(constants);
		config.configLuceneX(baseConfig);
	}

	public static Constants baseConfig() {
		return constants;
	}

	public void createSource(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer,Class<?> clas,Dic dic) {
		try {
			SourceConfig config = new SourceConfig();
			if(StringUtils.isBlank(indexPath)) {
				if(StringUtils.isNotBlank(constants.getDefaultDisk())) {
					indexPath = constants.getDefaultDisk();
				}else {
					throw new NullPointerException("There is no default disk");
				}
			}
			String path = indexPath+dataKey;
			File indexDirectory = new File(path);
			if(!indexDirectory.exists()) {
				indexDirectory.mkdirs();
			}
			if(!indexDirectory.isDirectory()) {
				throw new Exception("Not a valid directory");
			}
			if(analyzer == null) {
				analyzer = new PerFieldAnalyzerWrapper(new LDAnalyzer());
			}
			if(clas == null && constants.getDefaultClass() == null) {
				throw new NullPointerException("No default class");
			}
			if(clas == null) {
				if(constants.getDefaultClass() == null) {
					throw new NullPointerException("No default class");
				}else {
					clas = constants.getDefaultClass();
				}
			}
			config.setHighlight(highlight);
			config.setDefaultClass(clas);
			if(dic != null) {
				dic.init();
				config.setDic(dic);
			}
			config.setAnalyzer(analyzer);
			setWriter(config, indexDirectory.toPath());
			config.restartReader();
			ManySource.putDataSource(dataKey, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWriter(SourceConfig config,Path path) throws IOException {
		FSDirectory directory = NIOFSDirectory.open(path, NoLockFactory.INSTANCE);
		IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(config.getAnalyzer()));
		config.setWriter(writer);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param dataKey
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String)
	 */
	@Override
	public void add(String dataKey) {
		createSource(null, dataKey, constants.isHighlight(), null, null, null);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param dataKey
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String dataKey, Dic dic) {
		createSource(null, dataKey, constants.isHighlight(), null, null, dic);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param dataKey
	 * @param clas
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.Class)
	 */
	@Override
	public void add(String dataKey, Class<?> clas) {
		createSource(null, dataKey, constants.isHighlight(), null, clas, null);		
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param dataKey
	 * @param clas
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.Class, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String dataKey, Class<?> clas, Dic dic) {
		createSource(null, dataKey, constants.isHighlight(), null, clas, dic);		
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String)
	 */
	@Override
	public void add(String indexPath, String dataKey) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, constants.isHighlight(), null, null, null);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String indexPath, String dataKey, Dic dic) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, constants.isHighlight(), null, null, dic);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param clas
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@Override
	public void add(String indexPath, String dataKey, Class<?> clas) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, constants.isHighlight(), null, clas, null);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param clas
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, java.lang.Class, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String indexPath, String dataKey, Class<?> clas, Dic dic) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, constants.isHighlight(), null, clas, dic);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, null, null, null);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, Dic dic) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, null, null, dic);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param clas
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, java.lang.Class)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, Class<?> clas) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, null, clas, null);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param clas
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, java.lang.Class, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, Class<?> clas, Dic dic) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, null, clas, dic);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param analyzer
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, analyzer, null, null);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param analyzer
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer, Dic dic) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, analyzer, null, dic);
	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param analyzer
	 * @param clas
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper, java.lang.Class)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer,
			Class<?> clas) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, analyzer, clas, null);

	}

	/* (non Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param indexPath
	 * @param dataKey
	 * @param highlight
	 * @param analyzer
	 * @param clas
	 * @param dic
	 * @see com.ld.lucenex.base.InitConfig#add(java.lang.String, java.lang.String, boolean, org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper, java.lang.Class, com.ld.lucenex.analyzer.Dic)
	 */
	@Override
	public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer,
			Class<?> clas, Dic dic) {
		// TODO 自动生成的方法存根
		createSource(indexPath, dataKey, highlight, analyzer, clas, dic);

	}

}
interface InitConfig {

	void add(String dataKey);
	void add(String dataKey,Dic dic);
	void add(String dataKey,Class<?> clas);
	void add(String dataKey,Class<?> clas,Dic dic);

	void add(String indexPath,String dataKey);
	void add(String indexPath,String dataKey,Dic dic);
	void add(String indexPath,String dataKey,Class<?> clas);
	void add(String indexPath,String dataKey,Class<?> clas,Dic dic);

	void add(String indexPath,String dataKey,boolean highlight);
	void add(String indexPath,String dataKey,boolean highlight,Dic dic);
	void add(String indexPath,String dataKey,boolean highlight,Class<?> clas);
	void add(String indexPath,String dataKey,boolean highlight,Class<?> clas,Dic dic);

	void add(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer);
	void add(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer,Dic dic);
	void add(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer,Class<?> clas);
	void add(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer,Class<?> clas,Dic dic);

}
