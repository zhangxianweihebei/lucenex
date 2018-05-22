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

import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;

import com.ld.lucenex.config.Constants;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.config.SourceConfig;

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

	public void createSource(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer,Class<?> clas) {
		SourceConfig config = new SourceConfig();
	}

	@Override
	public void add(String dataKey) {
		createSource(null, dataKey, false, null, null);
	}

	@Override
	public void add(String dataKey, Class<?> clas) {
		createSource(null, dataKey, false, null, clas);
	}

	@Override
	public void add(String indexPath, String dataKey) {
		createSource(indexPath, dataKey, false, null, null);
	}

	@Override
	public void add(String indexPath, String dataKey, Class<?> clas) {
		createSource(indexPath, dataKey, false, null, clas);
	}

	@Override
	public void add(String indexPath, String dataKey, boolean highlight) {
		createSource(indexPath, dataKey, highlight, null, null);
	}

	@Override
	public void add(String indexPath, String dataKey, boolean highlight, Class<?> clas) {
		createSource(indexPath, dataKey, highlight, null, clas);
	}

	@Override
	public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer) {
		createSource(indexPath, dataKey, highlight, analyzer, null);
		
	}

	@Override
	public void add(String indexPath, String dataKey, boolean highlight, PerFieldAnalyzerWrapper analyzer,
			Class<?> clas) {
		createSource(indexPath, dataKey, highlight, analyzer, clas);
	}
}
interface InitConfig {
	
	void add(String dataKey);
	void add(String dataKey,Class<?> clas);
	
	void add(String indexPath,String dataKey);
	void add(String indexPath,String dataKey,Class<?> clas);
	
	void add(String indexPath,String dataKey,boolean highlight);
	void add(String indexPath,String dataKey,boolean highlight,Class<?> clas);
	
	void add(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer);
	void add(String indexPath,String dataKey,boolean highlight, PerFieldAnalyzerWrapper analyzer,Class<?> clas);

}
