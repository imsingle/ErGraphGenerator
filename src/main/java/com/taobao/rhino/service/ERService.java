package com.taobao.rhino.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableList;
import com.taobao.rhino.model.er.*;
import com.taobao.rhino.model.er.view.ERDEntityView;
import com.taobao.rhino.model.er.view.UMLNoteLinkView;
import com.taobao.rhino.model.er.view.UMLNoteView;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;

/**
 * <p>
 * ER模型对应数据库模型
 * <p>
 * ProjectNode - 数据库实例
 * <p>
 * DataModel - db
 * <p>
 * Entity - table
 * <p>
 * Diagram - View
 * <p>
 *
 * @author xueshengguo
 * @date 2018/11/23
 */
public class ERService {

    /**
     * 记录各节点信息，方便查找，String 是node的name属性
     */
    @Getter
    private Map<String, ProjectNode> projects = new HashMap<>();
    @Getter
    private Map<String, List<ERDDataModel>> dbs = new HashMap<>();
    @Getter
    private Map<String, List<ERDEntity>> tables = new HashMap<>();
    @Getter
    private Map<String, List<ERDDiagram>> diagrams = new HashMap<>();

    /**
     * 原始数据
     */
    private static String SOURCE_FILE = "origin.mdj";

    @Getter
    private ProjectNode projectNode;

    /**
     * 导入原始数据
     *
     * @throws IOException
     */
    public ERService() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(SOURCE_FILE);
        if (inputStream != null) {
            projectNode = JSON.parseObject(inputStream, ProjectNode.class, Feature.DisableSpecialKeyDetect,
                    Feature.DisableCircularReferenceDetect, Feature.IgnoreNotMatch);
            loadProjectNode(projectNode);
        } else {
            System.out.println("初始mdj文件不存在!!");
            System.exit(1);
        }
    }

    private ERService(boolean empty) {
        projectNode = new ProjectNode();
        loadProjectNode(projectNode);
    }

    public static ERService empty() {
        ERService erService = new ERService(true);
        return erService;
    }

    public ERService(String sourcePath) {
        projectNode = JSON.parseObject(sourcePath, ProjectNode.class, Feature.DisableSpecialKeyDetect,
                Feature.DisableCircularReferenceDetect, Feature.IgnoreNotMatch);
        loadProjectNode(projectNode);
    }

    /**
     * 加载project信息
     *
     * @param projectNode
     */
    public void loadProjectNode(ProjectNode projectNode) {
        projects.put(projectNode.getName(), projectNode);
        projectNode.getOwnedElements().forEach(
                db -> {
                    loadDb(db);
                    db.getOwnedElements().forEach(
                            table -> loadTable(table)
                    );
                }
        );
    }

    /**
     * 加载db信息
     *
     * @param db
     */
    public void loadDb(ERDDataModel db) {
        List<ERDDataModel> dbList;
        if (dbs.containsKey(db.getName())) {
            dbList = dbs.get(db.getName());
            //判断dbList是否存在，存在且属于同一个库(库名和parentId相同)，则覆盖；不存在，则添加
            Optional optional = dbList.stream().filter(it -> it.getParent().getRef().equals(db.getParent().getRef())).findFirst();
            if (optional.isPresent()) {
                int index = dbList.indexOf(optional.get());
                dbList.set(index, db);
            } else {
                dbList.add(db);
            }
        } else {
            dbList = new ArrayList();
            dbList.add(db);
            dbs.put(db.getName(), dbList);
        }
    }

    /**
     * 加载table信息
     *
     * @param entity
     */
    public void loadTable(DataModelChildNode entity) {
        if (entity instanceof ERDEntity) {
            ERDEntity table = (ERDEntity) entity;
            List<ERDEntity> tableList;
            if (tables.containsKey(table.getName())) {
                tableList = tables.get(table.getName());
                //判断tableList是否存在，存在且属于同一个表(表名和parentId相同)，则覆盖；不存在，则添加
                Optional optional = tableList.stream().filter(it -> it.getParent().getRef().equals(table.getParent().getRef())).findFirst();
                if (optional.isPresent()) {
                    int index = tableList.indexOf(optional.get());
                    tableList.set(index, table);
                } else {
                    tableList.add(table);
                }
            } else {
                tableList = new ArrayList();
                tableList.add(table);
                tables.put(table.getName(), tableList);
            }
        } else {
            ERDDiagram diagram = (ERDDiagram) entity;
            List<ERDDiagram> diagramList;
            if (diagrams.containsKey(diagram.getName())) {
                diagramList = diagrams.get(diagram.getName());

                //判断diagramList是否存在，存在且属于同一个视图(视图名和parentId相同)，则覆盖；不存在，则添加
                Optional optional = diagramList.stream().filter(it -> it.getParent().getRef().equals(diagram.getParent().getRef())).findFirst();
                if (optional.isPresent()) {
                    int index = diagramList.indexOf(optional.get());
                    diagramList.set(index, diagram);
                } else {
                    diagramList.add(diagram);
                }
            } else {
                diagramList = new ArrayList();
                diagramList.add(diagram);
                diagrams.put(diagram.getName(), diagramList);
            }
        }
    }

    /**
     * 根据projectId和dbName获取DB节点
     *
     * @param projectId
     * @param dbName
     * @return
     */
    public ERDDataModel getDbByName(String projectId, String dbName) {
        List<ERDDataModel> list = dbs.get(dbName);
        if (projectId == null || list == null) {
            return null;
        }
        return list.stream()
                .filter(item -> projectId.equals(item.getParent().getRef()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据dbId获取DB节点
     *
     * @param dbId
     * @return
     */
    public ERDDataModel getDbById(String dbId) {
        return dbs.values().stream()
                .flatMap(list -> list.stream())
                .filter(it -> it.getId().equals(dbId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据dbId和tableName获取table
     *
     * @param parentId
     * @param tableName
     * @return
     */
    public ERDEntity getTableByName(String parentId, String tableName) {
        List<ERDEntity> list = tables.get(tableName);
        if (parentId == null || list == null) {
            return null;
        }
        return list.stream()
                .filter(item -> parentId.equals(item.getParent().getRef()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据projectId获取ProjectNode
     *
     * @param id
     * @return
     */
    public ProjectNode getProjectById(String id) {
        return projects.values().stream()
                .filter(item -> item.getId().equals(id))
                .map(entry -> projects.get(entry.getName()))
                .findFirst()
                .orElse(null);
    }

    public ERDEntityView createViewEntity(ERDDiagram diagram, String entityId) {
        ERDEntityView node = new ERDEntityView(diagram.getId(), entityId);
        diagram.addERDEntityView(node);
        return node;
    }

    public UMLNoteView createViewNot(String text, ERDDiagram diagram) {
        UMLNoteView noteView = new UMLNoteView(text, diagram.getId());
        diagram.addERDEntityView(noteView);
        return noteView;
    }

    /**
     * 头尾的元素id
     *
     * @param headId
     * @param tailId
     * @return
     */
    public UMLNoteLinkView createViewLine(String headId, String tailId, ERDDiagram diagram) {
        UMLNoteLinkView linkView = new UMLNoteLinkView(headId, tailId, diagram.getId());
        diagram.addERDEntityView(linkView);
        return linkView;
    }

    public ERDDiagram createDiagram(String name, String dbId) {
        return createDiagram(name, "", dbId);
    }

    /**
     * 创建图表，并挂在到db下
     *
     * @param name
     * @param document
     * @param dbId
     * @return
     */
    public ERDDiagram createDiagram(String name, String document, String dbId) {
        ERDDiagram node = ERDDiagram.newInstanct(name, document, dbId);
        if (!diagrams.containsKey(name)) {
            diagrams.put(name, new ArrayList<>());
        }
        List list = diagrams.get(name);
        list.add(node);

        ERDDataModel dataModel = getDbById(dbId);
        dataModel.addOwnedElement(node);
        return node;
    }

    /**
     * 创建表，并挂在到db下
     *
     * @param name
     * @param document
     * @param dbId
     * @return
     */
    public ERDEntity createTable(String name, String document, String dbId) {
        ERDEntity node = ERDEntity.newInstanct(name, document, dbId);
        if (!tables.containsKey(name)) {
            tables.put(name, new ArrayList<>());
        }
        List list = tables.get(name);
        list.add(node);

        ERDDataModel dataModel = getDbById(dbId);
        dataModel.addOwnedElement(node);
        return node;
    }

    /**
     * 创建一个db，并挂在到project节点下
     *
     * @param name
     * @return
     */
    public ERDDataModel createDatabase(String name, String projectId) {
        ERDDataModel node = new ERDDataModel(name, projectId);
        if (!dbs.containsKey(name)) {
            dbs.put(name, new ArrayList<>());
        }
        List list = dbs.get(name);
        list.add(node);

        ProjectNode projectNode = getProjectById(projectId);
        projectNode.addOwnedElement(node);
        return node;
    }

    /**
     * 创建一个project节点
     *
     * @param name
     * @return
     */
    public ProjectNode createProject(String name) {
        ProjectNode node = new ProjectNode();
        node.setName(name);
        projects.put(name, node);
        return node;
    }

    public static void save(Path outputPath, String projectNode) throws IOException {
        List<String> lastJson = ImmutableList.of(projectNode);
        Files.write(outputPath, lastJson);
    }

    /**
     * 输出mdj文件, 先备份原文件，再讲projectNode写入当前文件
     */
    public void saveER() {
        String targetFile = "output/backup-" + System.currentTimeMillis() + ".mdj";
        Path targetPath = Paths.get(targetFile);

        URI copySourceURI;
        try {
            copySourceURI = Thread.currentThread().getContextClassLoader().getResource(SOURCE_FILE).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("路径" + targetFile + "不存在", e);
        }
        Path copySourcePath = Paths.get(copySourceURI);
        String writeLastMdj = "output/last.mdj";
        Path writeLastMdjPath = Paths.get(writeLastMdj);
        try {
            Files.copy(copySourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            List<String> lastJson = ImmutableList.of(JSON.toJSONString(projectNode, SerializerFeature.PrettyFormat));
            Files.write(copySourcePath, lastJson, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(writeLastMdjPath, lastJson, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("lastJson: " + lastJson);
        } catch (IOException e) {
            throw new RuntimeException("文件存储失败", e);
        }
    }
}
