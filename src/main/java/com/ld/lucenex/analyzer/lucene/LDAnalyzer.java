package com.ld.lucenex.analyzer.lucene;





import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;

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
	public LDAnalyzer(){
		this(false);
	}

	/**
	 * 
	 * @param useSmart true（智能分词）false（细粒度切分算法）
	 */
	public LDAnalyzer(boolean useSmart){
		super();
		this.useSmart = useSmart;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Reader reader=new StringReader(fieldName);
		LDTokenizer ikTokenizer = new LDTokenizer(reader, this.useSmart);
		return new TokenStreamComponents(ikTokenizer);
	}

}
