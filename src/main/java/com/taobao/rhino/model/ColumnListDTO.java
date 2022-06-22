package com.taobao.rhino.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
@Data
public class ColumnListDTO {
    private String csrf;
    private List<ColumnMeta> root;

    /**
     * 字段元信息
     */
    @Data
    public static class ColumnMeta {
        private Boolean autoIncrement;
        private String columnName;
        private String columnType;
        private Integer dataPrecision;
        private Integer dataScale;
        private String description;
        private String extra;
        private Boolean follow;
        private Boolean generationColumn;
        private String generationExpression;
        private Date gmtCreate;
        private Date gmtModified;
        private Long id;
        private Boolean logic;
        private Boolean nullable;
        private String securityLevel;
        private Boolean sensitive;
    }
}
