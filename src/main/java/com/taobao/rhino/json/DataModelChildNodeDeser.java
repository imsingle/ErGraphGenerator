package com.taobao.rhino.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xueshengguo
 * @date 2018/11/28
 */
public class DataModelChildNodeDeser implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        List<JSONObject> list = parser.parseArray(JSONObject.class);
//parser.parseArray(new Type[]{LabelView.class, ERDEntity.class})

        return (T)list.stream().map(it -> {
            String nodeType = it.getString("_type");
            try {
                Class typeClass = Class.forName("com.taobao.rhino.model.er." + nodeType);
                return JSON.parseObject(it.toJSONString(), typeClass, Feature.DisableSpecialKeyDetect);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("未识别类型:" + nodeType, e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
