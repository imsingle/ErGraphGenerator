package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class UMLNoteView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "UMLNoteView";
    private String text;

    public UMLNoteView(String text, String parentId) {
        super.setParent(new Parent(parentId));
        this.text = text;
    }
}
