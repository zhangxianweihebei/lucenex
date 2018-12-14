package com.ld.lucenex.plugin;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.ManySource;

import javax.servlet.*;
import java.io.IOException;

/**
 * 通用 web 集成方式
 */
public class WebCommonPlugin implements Filter {

    LuceneXConfig config;

    public WebCommonPlugin(LuceneXConfig config){
        this.config = config;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        BaseConfig.configLuceneX(config);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        ManySource.close();
        this.config = null;
    }
}
