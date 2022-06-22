package com.taobao.rhino;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.taobao.rhino.model.er.ERDDataModel;
import com.taobao.rhino.model.er.ERDDiagram;
import com.taobao.rhino.model.er.ERDEntity;
import com.taobao.rhino.model.er.ERDEntityColumn;
import com.taobao.rhino.model.er.ProjectNode;
import com.taobao.rhino.model.er.view.ERDEntityView;
import com.taobao.rhino.model.er.view.UMLNoteView;
import com.taobao.rhino.service.ERService;
import lombok.var;

import static com.taobao.rhino.IdbConstant.VIEW_CANVAS_MAX_LEFT;
import static com.taobao.rhino.IdbConstant.VIEW_CANVAS_MAX_LINE_COUNT;
import static com.taobao.rhino.IdbConstant.VIEW_CANVAS_MAX_SPACING;
import static com.taobao.rhino.IdbConstant.VIEW_CANVAS_MAX_TOP;

/**
 * Hello world!
 */
public class ERSupport {

    /**
     * 去掉`符号
     *
     * @param sourceStr
     * @return
     */
    public static String removeAccent(String sourceStr) {
        return sourceStr.replaceAll("[`|']", "");
    }

    /**
     * 获取结果输出目录
     *
     * @param inputPath
     * @param fileName
     * @return
     */
    public static Path getOutputPath(String inputPath, String fileName) {
        if (!inputPath.endsWith("/") && !fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        String savePath = String.format("%s/%s-%d.mdj", inputPath, fileName, System.currentTimeMillis()).replaceAll(
            "//", "/");
        return Paths.get(savePath);
    }

    /**
     * 获取sql文件输入目录
     *
     * @param args
     * @return
     */
    public static Path getInputPath(String[] args) {
        if (args.length == 0) {
            System.out.println("输入sql文件位置");
            System.exit(0);
        }
        String sqlPath = args[0];
        if (!sqlPath.startsWith("/")) {
            sqlPath = System.getProperty("user.dir") + "/" + args[0];
        }
        //文件不存在
        Path inputPath = Paths.get(sqlPath);
        if (!inputPath.toFile().exists()) {
            System.out.println(sqlPath + "文件不存在");
            System.exit(1);
        }
        return inputPath;
    }

    /**
     * 生成ProjectNode
     *
     * @param erService
     * @param dbName
     * @param createTableStatements
     * @return
     */
    public static ProjectNode generate(ERService erService, String dbName, List<SQLStatement> createTableStatements) {
        ProjectNode projectNode = erService.getProjectNode();
        ERDDataModel dataModel = erService.createDatabase(dbName, projectNode.getId());
        ERDDiagram diagram = erService.createDiagram(dbName, dataModel.getId());

        if (createTableStatements != null) {
            filter(createTableStatements).stream()
                .forEach(it -> {
                    if (it instanceof MySqlCreateTableStatement) {
                        MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement)it;

                        String tableName = ERSupport.removeAccent(createTableStatement.getName().getSimpleName());
                        String tableComment = createTableStatement.getComment() == null ? null
                            : createTableStatement.getComment().toString().replaceAll("^'|'$", "");
                        ERDEntity table = erService.createTable(tableName, tableComment,
                            dataModel.getId());

                        //创建view
                        ERDEntityView entityView = erService.createViewEntity(diagram, table.getId());
                        UMLNoteView noteView = erService.createViewNot(getNote(createTableStatement), diagram);
                        erService.createViewLine(entityView.getId(), noteView.getId(), diagram);
                        //创建view

                        //添加字段
                        List<ERDEntityColumn> columns = createTableStatement.getTableElementList().stream()
                            .filter(sqlTableElement -> sqlTableElement instanceof SQLColumnDefinition)
                            .map(sqlTableElement -> (SQLColumnDefinition)sqlTableElement)
                            .map(columnDefinition -> {
                                String columnName = ERSupport.removeAccent(columnDefinition.getNameAsString());
                                String columnType = columnDefinition.getDataType().getName();
                                String length = "";
                                if (columnDefinition.getDataType().getArguments() != null
                                    && columnDefinition.getDataType().getArguments().size() > 0) {
                                    length = columnDefinition.getDataType().getArguments().get(0).toString();
                                }
                                String comment = columnDefinition.getComment() == null ? null
                                    : columnDefinition.getComment().toString().replaceAll("^'|'$", "");
                                return ERDEntityColumn.newInstanct(columnName, columnType, length, comment,
                                    table.getId());
                            }).collect(Collectors.toList());
                        table.setColumns(columns);
                    }
                });

            //重置view的位置
            if (diagram.getOwnedViews().size() < 30) {
                double avgLeft = VIEW_CANVAS_MAX_LEFT / VIEW_CANVAS_MAX_LINE_COUNT;
                var counter = new AtomicInteger(1);
                diagram.getOwnedViews().stream()
                    .forEach(view -> {
                        int positionIndex = counter.get() % VIEW_CANVAS_MAX_LINE_COUNT;
                        int lineNum = counter.getAndIncrement() / VIEW_CANVAS_MAX_LINE_COUNT + 1;
                        double top = lineNum * VIEW_CANVAS_MAX_SPACING * 1.0;
                        double left = positionIndex * avgLeft;
                        view.setTop(top > VIEW_CANVAS_MAX_TOP ? VIEW_CANVAS_MAX_TOP : top);
                        view.setLeft(left > VIEW_CANVAS_MAX_LEFT ? VIEW_CANVAS_MAX_LEFT : left);
                    });
            }
        }
        return projectNode;
    }

    /**
     * 表字段注释
     *
     * @param createTableStatement
     * @return
     */
    public static String getNote(MySqlCreateTableStatement createTableStatement) {
        List<String> comments = new ArrayList<>();
        if (createTableStatement.getComment() != null) {
            comments.add(createTableStatement.getComment().toString());
        }
        List<String> commentList = createTableStatement.getTableElementList().stream()
            .filter(tableElement -> tableElement instanceof SQLColumnDefinition)
            .map(tableElement -> (SQLColumnDefinition)tableElement)
            .map(SQLColumnDefinition::getComment)
            .map(Objects::toString)
            .map(ERSupport::removeAccent)
            .collect(Collectors.toList());
        comments.addAll(commentList);
        return Joiner.on("\n").join(comments);
    }

    /**
     * 过滤需要生成的表，分表合成一张表，并取消分表后缀"_0000/_0001"等
     *
     * @param createTableStatements
     * @return
     */
    public static List<SQLStatement> filter(List<SQLStatement> createTableStatements) {
        Set<String> tables = Sets.newHashSet();
        return createTableStatements.stream()
            .map(item -> (MySqlCreateTableStatement)item)
            .filter(item -> {
                String tableName = item.getTableSource().getName().getSimpleName().replaceAll("`", "").replaceAll(
                    "_\\d+$", "");

                if (tables.contains(tableName)) {
                    return false;
                } else {
                    tables.add(tableName);
                    item.setName(tableName);
                    return true;
                }
            })
            .collect(Collectors.toList());
    }
}
