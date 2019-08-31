package com.ld.lucenex.demo;

import com.ld.lucenex.service.ServiceImpl;

public class DemoService<T> extends ServiceImpl<T> {
    public DemoService(String sourceKey) {
        super(sourceKey);
    }
}
