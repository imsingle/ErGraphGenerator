package com.taobao.rhino.service;

import com.google.common.collect.ImmutableList;
import com.taobao.rhino.model.er.ERDDataModel;
import com.taobao.rhino.model.er.ERDEntity;
import com.taobao.rhino.model.er.ERDEntityColumn;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ERServiceTest {

    @Test
    public void getDb() {
    }

    @Test
    public void getDbById() {
    }

    @Test
    public void getTableByName() {
    }

    @Test
    public void getProjectById() {
    }

    @Test
    public void createTable() {
    }

    @Test
    public void createDatabase() {
    }

    @Test
    public void createProject() {
    }

    @Test
    public void loadProjectNode() {
    }

    @Test
    public void loadTable() {
    }

    @Test
    public void loadDb() {
    }

    @Test
    public void saveER() {
    }




    private ERService service;

    @Before
    public void before() {
        try {
            service = new ERService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void demoInsert() {
        //ParserConfig.getGlobalInstance().putDeserializer(Parent.class, new ParentDeser());

        //新增DB
        String dbName = "demo2"+System.currentTimeMillis();
        ERDDataModel dataModel = service.getDbByName(service.getProjectNode().getId(), dbName);
        if (dataModel == null) {
            dataModel = service.createDatabase(dbName, service.getProjectNode().getId());
        }

        //新增表
        String tableName = "book";
        ERDEntity table = service.getTableByName(dataModel.getId(), tableName);
        if (table == null) {
            table = service.createTable(tableName, "书本信息", dataModel.getId());
        }

        //新增字段
        ERDEntityColumn column1 = ERDEntityColumn.newInstanct("id", "int", "20", "主键", table.getId());
        ERDEntityColumn column2 = ERDEntityColumn.newInstanct("name", "varchar", "50", "书名", table.getId());
        ERDEntityColumn column3 = ERDEntityColumn.newInstanct("price", "varchar", "50", "价格", table.getId());
        table.setColumns(ImmutableList.of(column1, column2, column3));
    }

    public void demoUpdate(ERService service) {
        String targetDb = "辅料库ER图";
        String targetTable = "f_accessory1";

        //新增字段或增加字段
        ERDDataModel dataModel = service.getDbByName(service.getProjectNode().getId(), targetDb);
        if (dataModel == null) {
            dataModel = service.createDatabase(targetDb, service.getProjectNode().getId());
        }

        ERDEntity table = service.getTableByName(dataModel.getId(), targetTable);
        if (table == null) {
            table = service.createTable(targetTable, "辅料表", dataModel.getId());
        }

        ERDEntityColumn column1 = ERDEntityColumn.newInstanct("id", "int", "20", "主键", table.getId());
        ERDEntityColumn column2 = ERDEntityColumn.newInstanct("name", "varchar", "50", "书名", table.getId());
        ERDEntityColumn column3 = ERDEntityColumn.newInstanct("price", "varchar", "50", "价格", table.getId());
        table.setColumns(ImmutableList.of(column1, column2, column3));
    }
}