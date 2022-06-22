package com.taobao.rhino.util;

import com.alibaba.druid.util.StringUtils;
import com.taobao.rhino.model.DatabaseViewDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xueshengguo
 * @date 2018/11/29
 */
@UtilityClass
public class IdbUtil {

    /**
     * 猜测逻辑库和非逻辑库的关系，根据名字
     *
     * @param dtoList
     * @return
     */
    public static Map<String, List<DatabaseViewDTO>> guessRelationShip(List<DatabaseViewDTO> dtoList) {
        Map<String, List<DatabaseViewDTO>> dbs = new HashMap<>();
        //逻辑库列表
        dtoList.stream().filter(DatabaseViewDTO::isLogic).forEach(
            db -> {
                List list = new ArrayList();
                list.add(db);
                dbs.put(db.getName(), list);
            }
        );
        dtoList.stream().filter(it -> !it.isLogic()).forEach(
            db -> {
                String table = getSimilarTable(db.getName());
                if (dbs.containsKey(table)) {
                    List list = dbs.get(table);
                    list.add(db);
                } else {
                    List list = new ArrayList();
                    list.add(db);
                    dbs.put(db.getName(), list);
                }
            }
        );
        return dbs;
    }

    /**
     * 获取分库对应的逻辑库名字
     *
     * @param dbName
     * @return
     */
    private String getSimilarTable(String dbName) {
        if (StringUtils.isEmpty(dbName)) {
            return dbName;
        }
        String guessName = dbName.replaceAll("_\\d+$", "");
        return guessName;
    }

}
