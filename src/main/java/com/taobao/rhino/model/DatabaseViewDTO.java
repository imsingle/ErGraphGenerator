package com.taobao.rhino.model;

import lombok.Data;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
@Data
public class DatabaseViewDTO {
    private Integer id;
    private String name;
    private String logic;
    private String env;

    @Override
    public String toString() {
        String SEPARATOR = "\t";
        return id + SEPARATOR + name + SEPARATOR + logic + SEPARATOR + env;
    }

    public Boolean isLogic() {
        return logic.equals("逻辑库");
    }
}
