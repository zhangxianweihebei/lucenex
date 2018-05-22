package com.ld.lucenex.field;





import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段类型注解
 * @author zxw
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldKey {
	
	/**
	 * 字段类型
	 * @return
	 */
	LDType type() default LDType.IntPoint;
	
	/**
	 * 排序方式
	 * @return
	 */
	LDSort sort() default LDSort.SortedDocValuesField;
	
	/**
	 * 是否高亮
	 * @return
	 */
	boolean highlight() default false;
	
	boolean pinyin() default false;

}
