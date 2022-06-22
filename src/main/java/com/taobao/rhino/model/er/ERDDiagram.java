package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.view.AbstractERDDiagramOwnedView;
import com.taobao.rhino.json.ERDDiagramOwnedViewDeser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ER图，画板
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDDiagram extends AbstractNode implements DataModelChildNode {
    @JSONField(name = "_type")
    private String type = "ERDDiagram";
    private String name;
    private String documentation;
    @JSONField(deserializeUsing = ERDDiagramOwnedViewDeser.class)
    private List<AbstractERDDiagramOwnedView> ownedViews = new ArrayList<>();

    public boolean addERDEntityView(AbstractERDDiagramOwnedView erdEntityView) {
        return ownedViews.add(erdEntityView);
    }

    public static ERDDiagram newInstanct(String name, String documentation, String parentId) {
        ERDDiagram diagram = new ERDDiagram();
        diagram.setParent(new Parent(parentId));
        diagram.setName(name);
        diagram.setDocumentation(documentation);
        return diagram;
    }
}
