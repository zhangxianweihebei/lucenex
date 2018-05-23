package com.ld.lucenex;

import com.ld.lucenex.field.FieldKey;
import com.ld.lucenex.field.LDType;

public class Empty {
	
	@FieldKey(type=LDType.IntPoint)
	private int id;
	@FieldKey(type=LDType.StringField)
	private String name;
	@FieldKey(type=LDType.TextField)
	private String text;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
