package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.relastion.ERDRelationship;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ER实例
 *
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDEntity extends AbstractNode implements DataModelChildNode {
    @JSONField(name = "_type")
    private String type = "ERDEntity";
    private String name;
    private String documentation;
    private List<ERDEntityColumn> columns = new ArrayList<>();
    private List<ERDRelationship> ownedElements = new ArrayList<>();

    public static ERDEntity newInstanct(String name, String Documentation, String parentId) {
        ERDEntity erdEntity = new ERDEntity();
        erdEntity.setParent(new Parent(parentId));
        erdEntity.setName(name);
        erdEntity.setDocumentation(Documentation);
        return erdEntity;
    }

    public boolean addColumn(ERDEntityColumn column) {
        return columns.add(column);
    }
}
