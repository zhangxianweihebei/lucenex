package com.ld.lucenex.analyzer.cfg;







import java.util.Set;

import com.ld.lucenex.analyzer.Dic;
import com.ld.lucenex.core.ManySource;

/**
 * Configuration 默认实现
 * 2012-5-8
 *
 */
public class DefaultConfig implements Configuration{
	

	/*
	 * 分词器默认字典路径 
	 */
	private static final String PATH_DIC_MAIN = "com/ld/lucenex/analyzer/main2012.dic";
	private static final String PATH_DIC_QUANTIFIER = "com/ld/lucenex/analyzer/quantifier.dic";

	//配置属性——扩展字典
	private static final String EXT_DICT = "ext_dict";
	//配置属性——扩展停止词典
	private static final String EXT_STOP = "ext_stopwords";

	/*
	 * 是否使用smart方式分词
	 */
	private boolean useSmart;

	/**
	 * 返回单例
	 * @param dicKey 
	 * @return Configuration单例
	 */
	public static Configuration getInstance(){
		return new DefaultConfig();
	}

	/*
	 * 初始化配置文件
	 */
	private DefaultConfig(){		
	}


	/**
	 * 返回useSmart标志位
	 * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
	 * @return useSmart
	 */
	public boolean useSmart() {
		return useSmart;
	}

	/**
	 * 设置useSmart标志位
	 * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
	 * @param useSmart
	 */
	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}	

	/**
	 * 获取主词典路径
	 * 
	 * @return String 主词典路径
	 */
	public String getMainDictionary(){
		return PATH_DIC_MAIN;
	}

	/**
	 * 获取量词词典路径
	 * @return String 量词词典路径
	 */
	public String getQuantifierDicionary(){
		return PATH_DIC_QUANTIFIER;
	}

	/**
	 * 获取扩展字典配置路径
	 * @return List<String> 相对类加载器的路径
	 */
	public Set<String> getExtDictionarys(){
		return build(EXT_DICT);
	}


	/**
	 * 获取扩展停止词典配置路径
	 * @return List<String> 相对类加载器的路径
	 */
	public Set<String> getExtStopWordDictionarys(){
		return build(EXT_STOP);

	}

	private Set<String> build(String key) {
		Dic dic = ManySource.getDataSource(ManySource.getContextHolder()) == null ? null : ManySource.getDataSource(ManySource.getContextHolder()).getDic();
		if(dic != null) {
			return dic.getThesaurus().get(key);
		}else {
			return null;
		}
	}


}
