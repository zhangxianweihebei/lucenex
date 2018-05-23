package com.ld.lucenex.analyzer.lucene;





import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;

import com.ld.lucenex.analyzer.cfg.DicDataSource;

public class LDAnalyzer extends Analyzer{

	private boolean useSmart;
	
	public boolean useSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	/**
	 * 默认细粒度切分算法
	 */
	public LDAnalyzer(String dicKey){
		this(false,dicKey);
	}

	/**
	 * 
	 * @param useSmart true（智能分词）false（细粒度切分算法）
	 */
	public LDAnalyzer(boolean useSmart,String dicKey){
		super();
		this.useSmart = useSmart;
		DicDataSource.setDatabaseType(dicKey);
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Reader reader=new StringReader(fieldName);
		LDTokenizer ikTokenizer = new LDTokenizer(reader, this.useSmart);
		return new TokenStreamComponents(ikTokenizer);
	}

}
