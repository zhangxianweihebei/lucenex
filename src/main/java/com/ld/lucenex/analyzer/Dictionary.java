package com.ld.lucenex.analyzer;







import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ld.lucenex.analyzer.cfg.Configuration;
import com.ld.lucenex.core.ManySource;


/**
 * 词典管理类,单子模式
 */
public class Dictionary {


	/*
	 * 词典单子实例
	 */
	private static Dictionary singleton;
	
	/*
	 * 主词典对象
	 */
	private Map<String, DictSegment> _MainDict;
	
	/*
	 * 停止词词典 
	 */
	private Map<String, DictSegment> _StopWordDict;
	/*
	 * 量词词典
	 */
	private DictSegment _QuantifierDict;
	
	/**
	 * 配置对象
	 */
	private Configuration cfg;
	
	private Dictionary(Configuration cfg){
		this.cfg = cfg;
		init();
		this.loadMainDict();
		this.loadStopWordDict();
		this.loadQuantifierDict();
	}
	void init(){
		if(_MainDict == null) {
			_MainDict = new HashMap<>();
		}
		if(_StopWordDict == null) {
			_StopWordDict = new HashMap<>();
		}
		if(_MainDict.get(ManySource.getContextHolder()) == null) {
			_MainDict.put(ManySource.getContextHolder(), new DictSegment((char)0));
		}
		if(_StopWordDict.get(ManySource.getContextHolder()) == null) {
			_StopWordDict.put(ManySource.getContextHolder(), new DictSegment((char)0));
		}
		if(_QuantifierDict == null) {
			_QuantifierDict = new DictSegment((char)0);
		}
	}
	
	/**
	 * 词典初始化
	 * 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
	 * 只有当Dictionary类被实际调用时，才会开始载入词典，
	 * 这将延长首次分词操作的时间
	 * 该方法提供了一个在应用加载阶段就初始化字典的手段
	 * @return Dictionary
	 */
	public static Dictionary initial(Configuration cfg){
		if(singleton == null || singleton._MainDict.get(ManySource.getContextHolder()) == null){
			synchronized(Dictionary.class){
				if(singleton == null || singleton._MainDict.get(ManySource.getContextHolder()) == null){
					singleton = new Dictionary(cfg);
					return singleton;
				}
			}
		}
		return singleton;
	}
	
	/**
	 * 获取词典单子实例
	 * @return Dictionary 单例对象
	 */
	public static Dictionary getSingleton(){
		if(singleton == null){
			throw new IllegalStateException("词典尚未初始化，请先调用initial方法");
		}
		return singleton;
	}
	
	/**
	 * 批量加载新词条
	 * @param words Collection<String>词条列表
	 */
	public void addWords(Collection<String> words){
		if(words != null){
			for(String word : words){
				if (word != null) {
					//批量加载词条到主内存词典中
					singleton._MainDict.get(ManySource.getContextHolder()).fillSegment(word.trim().toLowerCase().toCharArray());
				}
			}
		}
	}
	
	/**
	 * 批量移除（屏蔽）词条
	 * @param words
	 */
	public void disableWords(Collection<String> words){
		if(words != null){
			for(String word : words){
				if (word != null) {
					//批量屏蔽词条
					singleton._MainDict.get(ManySource.getContextHolder()).disableSegment(word.trim().toLowerCase().toCharArray());
				}
			}
		}
	}
	
	/**
	 * 检索匹配主词典
	 * @param charArray
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInMainDict(char[] charArray){
		return singleton._MainDict.get(ManySource.getContextHolder()).match(charArray);
	}
	
	/**
	 * 检索匹配主词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInMainDict(char[] charArray , int begin, int length){
		return singleton._MainDict.get(ManySource.getContextHolder()).match(charArray, begin, length);
	}
	
	/**
	 * 检索匹配量词词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInQuantifierDict(char[] charArray , int begin, int length){
		return singleton._QuantifierDict.match(charArray, begin, length);
	}
	
	
	/**
	 * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
	 * @param charArray
	 * @param currentIndex
	 * @param matchedHit
	 * @return Hit
	 */
	public Hit matchWithHit(char[] charArray , int currentIndex , Hit matchedHit){
		DictSegment ds = matchedHit.getMatchedDictSegment();
		return ds.match(charArray, currentIndex, 1 , matchedHit);
	}
	
	
	/**
	 * 判断是否是停止词
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return boolean
	 */
	public boolean isStopWord(char[] charArray , int begin, int length){			
		return singleton._StopWordDict.get(ManySource.getContextHolder()).match(charArray, begin, length).isMatch();
	}	
	
	/**
	 * 加载主词典及扩展词典
	 */
	private void loadMainDict(){
		//建立一个主词典实例
		DictSegment segment = _MainDict.get(ManySource.getContextHolder());
		//读取主词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cfg.getMainDictionary());
        if(is == null){
        	throw new RuntimeException("Main Dictionary not found!!!");
        }
        
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					segment.fillSegment(theWord.trim().toLowerCase().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Main Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//加载扩展词典
		this.loadExtDict();
	}	
	
	/**
	 * 加载用户配置的扩展词典到主词库表
	 */
	private void loadExtDict(){
		//加载扩展词典配置
		Set<String> extDictFiles  = cfg.getExtDictionarys();
		if(extDictFiles != null){
			for (String theWord : extDictFiles) {
				_MainDict.get(ManySource.getContextHolder()).fillSegment(theWord.trim().toLowerCase().toCharArray());
			}
		}		
	}
	
	/**
	 * 加载用户扩展的停止词词典
	 */
	private void loadStopWordDict(){
		//加载扩展停止词典
		Set<String> extStopWordDictFiles  = cfg.getExtStopWordDictionarys();
		if(extStopWordDictFiles != null){
			for (String theWord : extStopWordDictFiles) {
				_StopWordDict.get(ManySource.getContextHolder()).fillSegment(theWord.trim().toLowerCase().toCharArray());
			}
		}		
	}
	
	/**
	 * 加载量词词典
	 */
	private void loadQuantifierDict(){
		//读取量词词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cfg.getQuantifierDicionary());
        if(is == null){
        	throw new RuntimeException("Quantifier Dictionary not found!!!");
        }
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					_QuantifierDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
				}
			} while (theWord != null);
			
		} catch (IOException ioe) {
			System.err.println("Quantifier Dictionary loading exception.");
			ioe.printStackTrace();
			
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
