package com.ld.lucenex.field;


/**
 * 字段排序类型
 *
 * @author zxw
 */
public enum LDSort {


    /**
     * 按照byte[]列顺序进行排序/刻面
     */
    SortedDocValuesField,

    /**
     * 按照SortedSet<byte[]>列顺序进行排序/刻面
     */
    SortedSetDocValuesField,

    /**
     * 按照long列顺序进行排序/刻面
     */
    NumericDocValuesField,

    /**
     * 按照SortedSet<long>列顺序进行排序/刻面
     */
    SortedNumericDocValuesField,

    /**
     * null
     */
    SortNull

}
