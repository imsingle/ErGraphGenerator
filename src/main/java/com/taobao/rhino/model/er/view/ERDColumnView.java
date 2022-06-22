package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.model.er.Parent;
import lombok.Data;

/**
 * entity放到画布上，画布上entity的label信息
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ERDColumnView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "ERDColumnView";
    /**
     * ERDEntityColumn id
     */
    private Parent model;
}
