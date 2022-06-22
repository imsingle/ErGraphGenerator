package com.taobao.rhino.model.er.view;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * entity放到画布上，画布上entity的label信息
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class LabelView extends AbstractERDDiagramOwnedView {
    @JSONField(name = "_type")
    private String type = "LabelView";
    private String text;
}
