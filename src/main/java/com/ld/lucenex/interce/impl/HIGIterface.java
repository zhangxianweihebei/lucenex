/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: NRTIterface.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.interce.impl
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年11月27日 下午2:35:56
 * @version: V1.0
 */
package com.ld.lucenex.interce.impl;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.SourceConfig;
import com.ld.lucenex.core.ManySource;
import com.ld.lucenex.interce.LdInterface;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import java.lang.reflect.Method;

/**
 * @ClassName: NRTIterface
 * @Description: 高亮设置
 * @author: Myzhang
 * @date: 2018年11月27日 下午2:35:56
 */
public class HIGIterface implements LdInterface {
    @Override
    public boolean beforeMethod(Object obj, Method method, Object[] args) {
        SourceConfig dataSource = ManySource.getDataSource();
        if (!dataSource.isHighlight()) {
            return true;
        }
        String methodName = method.getName();
        if (methodName.indexOf("query") != -1 || methodName.indexOf("search") != -1
                || methodName.indexOf("find") != -1) {
            for (Object object : args) {
                if (object instanceof Query) {
                    String[] htmlFormatter = BaseConfig.baseConfig().getHtmlFormatter();
                    Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(htmlFormatter[0], htmlFormatter[1]), new QueryScorer((Query) object));
                    highlighter.setTextFragmenter(new SimpleFragmenter(BaseConfig.baseConfig().getHighlightNum()));
                    dataSource.setHighlighter(highlighter);
                    break;
                }
            }
        }
        return true;
    }

}
