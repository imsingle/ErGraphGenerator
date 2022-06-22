package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public class AbstractNode extends Node implements Serializable {
    @JSONField(name = "_parent")
    private Parent parent;
}
