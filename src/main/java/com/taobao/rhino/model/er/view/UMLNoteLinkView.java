package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class UMLNoteLinkView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "UMLNoteLinkView";
    private Parent head;
    private Parent tail;
    private Integer lineStyle = 1;
    private String points = "100:100;100:100";

    public UMLNoteLinkView(String headId, String tailId,String parentId) {
        super.setParent(new Parent(parentId));
        this.head = new Parent(headId);
        this.tail = new Parent(tailId);
    }
}
