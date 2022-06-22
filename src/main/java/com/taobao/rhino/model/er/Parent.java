package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class Parent implements Serializable {
    @JSONField(name = "$ref")
    private String ref;

    public Parent() {}

    public Parent(String id) {
        ref = id;
    }
}
