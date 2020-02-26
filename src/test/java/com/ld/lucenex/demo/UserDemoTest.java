package com.ld.lucenex.demo;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.ServiceFactory;
import com.ld.lucenex.service.ServiceImpl;

import java.io.IOException;

public class UserDemoTest {
    static LuceneX luceneX;
    static {
        luceneX = new LuceneX(new LuceneXConfig() {
            @Override
            public void configLuceneX(BaseConfig me) {
                me.add("D:\\data\\", "user", User.class);
            }
        });
    }

    public static void main(String[] a) throws IOException {
        User user = new User();
        user.setId(1);
        user.setName("zxw");
        ServiceImpl service = ServiceFactory.getService(ServiceImpl.class, "user");
        service.addObject(user);
        luceneX.close();
    }

}

class User{
    int id;
    String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
