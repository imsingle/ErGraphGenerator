package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDEntityColumn extends AbstractNode {
    @JSONField(name = "_type")
    private String type = "ERDColumn";

    /**
     * 字段类型
     */
    @JSONField(name = "type")
    private String columnType;
    private String name;
    private String documentation;
    private String length;
    private Boolean primaryKey;

    public static ERDEntityColumn newInstanct(String name, String type, String length, String documentation,
                                              String parentId) {
        return newInstanct(name, type, length, documentation, parentId, false);
    }

    public static ERDEntityColumn newInstanct(String name, String type, String length, String documentation,
                                              String parentId, Boolean primaryKey) {
        ERDEntityColumn erdEntityColumn = new ERDEntityColumn();
        erdEntityColumn.setParent(new Parent(parentId));
        erdEntityColumn.setName(name);
        erdEntityColumn.setColumnType(type);
        erdEntityColumn.setLength(length);
        erdEntityColumn.setDocumentation(documentation);
        erdEntityColumn.setPrimaryKey(primaryKey);

        return erdEntityColumn;
    }
}
