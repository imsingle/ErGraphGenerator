package com.taobao.rhino.model;

import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
@Data
public class TableViewDTO {
    /**
     * 表id
     */
    private Integer id;
    /**
     * 表名
     */
    private String name;
    /**
     * 所在库id
     */
    private Integer dbId;
    /**
     * 所在库名
     */
    private String dbName;

    @Override
    public String toString() {
        String SEPARATOR = "\t";
        return dbId + SEPARATOR + dbName + SEPARATOR + id + SEPARATOR + name;
    }
}
