package com.taobao.rhino.model;

import lombok.Data;

import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
@Data
public class TableListDTO {
    private String csrf;
    private Integer page;
    private Integer rows;
    private Boolean success;
    private Integer totalCount;
    private List<TableMeta> root;

    /**
     * 表元信息
     */
    @Data
    public static class TableMeta {
        private Integer dbId;
        private String dbType;
        private Boolean follow;
        private String guid;
        private String level;
        private Boolean logic;
        private List<String> ownerIds;
        private List<String> ownerNames;
        private List<String> permTypeList;
        private String routeKey;
        private String schemaName;
        private Integer tableCount;
        private String tableExpr;
        private Integer tableId;
        private String tableName;
        private String tablePattern;

        @Override
        public String toString() {
            String SEPARATOR = " ";
            return "\n" +
                dbId + SEPARATOR +
                dbType + SEPARATOR +
                follow + SEPARATOR +
                guid + SEPARATOR +
                level + SEPARATOR +
                logic + SEPARATOR +
                ownerIds + SEPARATOR +
                ownerNames + SEPARATOR +
                permTypeList + SEPARATOR +
                routeKey + SEPARATOR +
                schemaName + SEPARATOR +
                tableCount + SEPARATOR +
                tableExpr + SEPARATOR +
                tableId + SEPARATOR +
                tableName + SEPARATOR +
                tablePattern + SEPARATOR
                ;
        }
    }
}
