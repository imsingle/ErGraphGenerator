package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class ProjectNode extends Node {
    @JSONField(name = "_type")
    private String type = "Project";
    private String name;
    private List<ERDDataModel> ownedElements = new ArrayList<>();

    public boolean addOwnedElement(ERDDataModel erdDataModel) {
        return ownedElements.add(erdDataModel);
    }
}
