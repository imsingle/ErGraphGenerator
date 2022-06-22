package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.AbstractNode;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDRelationshipView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "ERDRelationshipView";
    /**
     * ERDRelationship的ID
     */
    private Parent model;
    /**
     * 上面的EntityView ID
     */
    private Parent head;
    /**
     * 下面的EntityView ID
     */
    private Parent tail;

    /**
     * 描述连接线的点的位置
     */
    private String points;

    /**
     * 这3个对应subViews里EdgeLabelView的ID
     */
    private Parent nameLabel;
    private Parent tailNameLabel;
    private Parent headNameLabel;
    private List<EdgeLabelView> subViews = new ArrayList();

    @Data
    static class EdgeLabelView extends AbstractNode {
        @JSONField(name = "_type")
        private String type = "UMLNoteView";
        private Boolean visible;
        private String font;
        private Integer left;
        private Integer top;
        private Integer height;
        private Double alpha;
        private Integer distance;
        private Parent hostEdge;
        private Integer edgePosition;
    }
}
