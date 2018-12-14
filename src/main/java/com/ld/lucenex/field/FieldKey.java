package com.ld.lucenex.field;


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
    LDType type() default LDType.IntPoint;

    /**
     * 排序方式
     *
     * @return
     */
    LDSort sort() default LDSort.SortedDocValuesField;
}
