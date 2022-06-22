package com.taobao.rhino.model.er.relastion;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.AbstractNode;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

@Data
public class ERDRelationshipEnd extends AbstractNode {
    @JSONField(name = "_type")
    private String type = "ERDRelationshipEnd";
    private Parent reference;
    private String cardinality;
}