package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * entity放到画布上，画布上entity的label信息
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDColumnCompartmentView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "ERDColumnCompartmentView";
    /**
     * ERDEntity id
     */
    private Parent model;
    private List subViews = new ArrayList<>();
}
