/**
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: Constants.java
 * @Prject: lucenex
 * @Package: com.ld.lucenex.config
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:20:11
 * @version: V1.0
 */
package com.ld.lucenex.base;

import com.ld.lucenex.interce.LdInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: Myzhang
 * @date: 2018年5月22日 下午6:20:11
 */
public final class Constants {

    private boolean devMode = Const.DEFAULT_DEV_MODE;
    private Class<?> defaultClass;
    private String defaultDisk;
    private boolean highlight = Const.DEFAULT_HIGHLIGHT;
    private String[] htmlFormatter = Const.DEFAULT_HTMLFORMATTER;
    private int HighlightNum = Const.DEFAULT_HighlightNum;
    private List<LdInterface> LdInterface = new ArrayList<>();

    /**
     * 添加一个拦截器
     *
     * @param ldInterface
     */
    public void addInterface(LdInterface ldInterface) {
        LdInterface.add(ldInterface);
    }

    /**
     * 添加一个指定位置的拦截器
     *
     * @param i
     * @param ldInterface
     */
    public void addInterface(int i, LdInterface ldInterface) {
        LdInterface.add(i, ldInterface);
    }

    /**
     * 获取拦截器集合
     *
     * @return
     */
    public List<LdInterface> getLdInterface() {
        return LdInterface;
    }


    /**
     * 设置是否为开发模式
     *
     * @param devMode
     */
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    /**
     * 获取高亮截词数量
     *
     * @param highlightNum
     */
    public void setHighlightNum(int highlightNum) {
        HighlightNum = highlightNum;
    }

    /**
     * 获取高亮截词数量
     *
     * @return
     */
    public int getHighlightNum() {
        return HighlightNum;
    }

    /**
     * 设置高亮标签
     *
     * @param prefix
     * @param suffix
     */
    public void setHtmlFormatter(String prefix, String suffix) {
        this.htmlFormatter = new String[]{prefix, suffix};
    }

    /**
     * 获取高亮标签
     */
    public String[] getHtmlFormatter() {
        return htmlFormatter;
    }

    /**
     * 获取当前启用模式
     */
    public boolean isDevMode() {
        return devMode;
    }

    /**
     * 设置存储类 class
     */
    public void setDefaultClass(Class<?> defaultClass) {
        this.defaultClass = defaultClass;
    }

    /**
     * 获取存储类 class
     */
    public Class<?> getDefaultClass() {
        return defaultClass;
    }

    /**
     * 设置是否高亮 true false
     */
    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    /**
     * 获取是否高亮
     */
    public boolean isHighlight() {
        return highlight;
    }

    /**
     * 设置默认存储目录
     */
    public void setDefaultDisk(String defaultDisk) {
        this.defaultDisk = defaultDisk;
    }

    /**
     * 获取默认存储目录
     */
    public String getDefaultDisk() {
        return defaultDisk;
    }

}
