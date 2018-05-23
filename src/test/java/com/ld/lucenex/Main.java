package com.ld.lucenex;

import java.io.IOException;

import com.ld.lucenex.core.LuceneX;

public class Main {
	public static void main(String[] args) throws IOException {
		LuceneX.start(DemoConfig.class);
//		new SaveIndex().save();
		new SerachEmpty().findList();
	}
}
