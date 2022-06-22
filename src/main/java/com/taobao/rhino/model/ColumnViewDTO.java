package com.taobao.rhino.model;

import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
@Data
public class ColumnViewDTO {
    /**
     * 字段id
     */
    private Long id;
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 字段注释
     */
    private String description;

    @Override
    public String toString() {
        String SEPARATOR = "\t";
        return id + SEPARATOR + columnName + SEPARATOR + columnType + SEPARATOR + description;
    }
}
