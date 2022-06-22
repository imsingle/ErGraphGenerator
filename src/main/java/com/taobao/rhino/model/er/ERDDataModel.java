package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.json.DataModelChildNodeDeser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDDataModel extends AbstractNode {
    @JSONField(name = "_type")
    private String type = "ERDDataModel";
    private String name;
    private String documentation;
    @JSONField(deserializeUsing = DataModelChildNodeDeser.class)
    private List<DataModelChildNode> ownedElements = new ArrayList<>();

    public ERDDataModel() {}

    public ERDDataModel(String name) {
        this.name = name;
    }

    public ERDDataModel(String name, String parentId) {
        this.name = name;
        setParent(new Parent(parentId));
    }

    public boolean addOwnedElement(DataModelChildNode ownedElement) {
        return ownedElements.add(ownedElement);
    }
}
