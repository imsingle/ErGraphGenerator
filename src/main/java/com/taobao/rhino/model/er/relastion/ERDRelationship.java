package com.taobao.rhino.model.er.relastion;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.AbstractNode;
import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDRelationship extends AbstractNode {
    @JSONField(name = "_type")
    private String type = "ERDRelationship";
    private String name;
    private ERDRelationshipEnd end1;
    private ERDRelationshipEnd end2;
}
