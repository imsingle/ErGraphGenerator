package com.taobao.rhino.model.er;

import com.alibaba.fastjson.annotation.JSONField;
import com.taobao.rhino.util.IdGenerator;
import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/23
 */
@Data
public abstract class Node {
    @JSONField(name = "_id")
    private String id = IdGenerator.newId();
}
