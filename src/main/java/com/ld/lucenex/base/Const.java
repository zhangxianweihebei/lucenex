/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: Const.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.base
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:21:00
 * @version: V1.0
 */
package com.ld.lucenex.base;

/**
 * @ClassName: Const
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:21:00
 */
public class Const {
    public final static boolean DEFAULT_DEV_MODE = true;
    public final static boolean DEFAULT_HIGHLIGHT = true;
    public final static String[] DEFAULT_HTMLFORMATTER = new String[]{"<span>", "</span>"};
    public final static int DEFAULT_HighlightNum = 30;

    /**
     * 默认根据系统智能选择
     * nio
     *simp
     * mmap
     */
    public static String FSD_TYPE="auto";

    public final static String STRING_TEXTFIELD_STR = "str";
    public final static String STRING_TEXTFIELD_TEXT = "text";
    public final static String DEFAULT_SERVICE_KEY = "default_service_key";


}
