package com.taobao.rhino;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.taobao.rhino.model.er.ProjectNode;
import com.taobao.rhino.service.ERService;

import java.util.List;

public class ERClient {

    /**
     * sql转成mdj
     *
     * @param dbName
     * @param sql
     * @return
     */
    public static void generateMdj(ERService erService, String dbName, String sql) {
        if (StringUtils.isEmpty(sql)) {
            return;
        }

        List<SQLStatement> createTableStatements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        ERSupport.generate(erService, dbName, createTableStatements);
    }

    /**
     * sql转成mdj
     *
     * @param dbName
     * @param sql
     * @return
     */
    public static String generateMdjs(String dbName, String sql) {
        if (StringUtils.isEmpty(sql)) {
            return "";
        }

        ERService erService = ERService.empty();
        List<SQLStatement> createTableStatements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        ProjectNode projectNode = ERSupport.generate(erService, dbName, createTableStatements);
        return JSON.toJSONString(projectNode, SerializerFeature.PrettyFormat);
    }
}
