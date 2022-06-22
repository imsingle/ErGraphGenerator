package com.taobao.rhino.model;

import lombok.Data;

import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
@Data
public class DatabaseListDTO {
    private String csrf;
    private Integer page;
    private Integer rows;
    private Boolean success;
    private Integer totalCount;
    private List<DatabaseMeta> root;

    /**
     * 表元信息
     */
    @Data
    public static class DatabaseMeta {
        private String clusterNode;
        private Integer dbId;
        private String dbType;
        private String description;
        private String envType;
        private Boolean follow;
        private String idc;
        private String idcTitle;
        private String level;
        private Boolean logic;
        private List<String> ownerIds;
        private List<String> ownerNames;
        private String permDetailType;
        private List<String> permDetailTypeList;
        private String permType;
        private List<String> permTypeList;
        private String schemaName;
        private String searchName;
        private Integer tableCount;
        private String unitType;

        @Override
        public String toString() {
            String SEPARATOR = " ";
            return "\n" +
                clusterNode + SEPARATOR +
                dbId + SEPARATOR +
                dbType + SEPARATOR +
                description + SEPARATOR +
                envType + SEPARATOR +
                follow + SEPARATOR +
                idc + SEPARATOR +
                idcTitle + SEPARATOR +
                level + SEPARATOR +
                logic + SEPARATOR +
                ownerIds + SEPARATOR +
                ownerNames + SEPARATOR +
                permDetailType + SEPARATOR +
                permDetailTypeList + SEPARATOR +
                permType + SEPARATOR +
                permTypeList + SEPARATOR +
                schemaName + SEPARATOR +
                tableCount + SEPARATOR +
                unitType + SEPARATOR
                ;
        }
    }
}
