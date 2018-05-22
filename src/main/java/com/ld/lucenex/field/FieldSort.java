package com.ld.lucenex.field;



import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;

/**
 * 排序 sort
 * @author zxw
 *
 */
public class FieldSort{
	
	private String[] fields;
	private Type[] types;
	private boolean[] reverses;
	
	public Sort sort() {
		Sort sort = new Sort();
		for(int i=0;i<this.fields.length;i++) {
			sort.setSort(new SortField(fields[i], types[i], reverses[i]));
		}
		return sort;
	}
}
