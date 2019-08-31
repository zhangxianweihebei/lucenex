package com.ld.lucenex.field;


import com.ld.lucenex.base.Const;

import java.lang.annotation.*;

/**
 * 字段类型注解
 *
 * @author zxw
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldKey {

    /**
     * 字段类型
     *
     * @return
     */
    LDType type() default LDType.StringField;

    /**
     * 排序方式
     *
     * @return
     */
    LDSort sort() default LDSort.SortedDocValuesField;

    boolean highlight() default false;

    String[] highlightTag() default {"<span>","</span>"};

    int highlightNum() default 30;
}
