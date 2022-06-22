package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.ERDEntitySubView;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDEntityView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "ERDEntityView";
    /**
     * ERDEntity id
     */
    @JSONField(name = "model")
    private Parent entityId;
    //private Parent nameLabel;
    //private Parent columnCompartment;
    private List<ERDEntitySubView> subViews = new ArrayList<>();

    public ERDEntityView() {}

    public ERDEntityView(String parentId, String entityId) {
        setParent(new Parent(parentId));
        this.entityId = new Parent(entityId);
    }

    public boolean addSubView(ERDEntitySubView subView) {
        return subViews.add(subView);
    }
}
