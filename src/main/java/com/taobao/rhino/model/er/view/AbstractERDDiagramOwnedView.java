package com.taobao.rhino.model.er.view;

import com.taobao.rhino.model.er.AbstractNode;
import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/28
 */
@Data
public abstract class AbstractERDDiagramOwnedView extends AbstractNode {
    private String font = "Arial;13;0";
    private Double left = 100.0;
    private Double top = 100.0;
    private Double width = 100.0;
    private Double height = 100.0;
}
