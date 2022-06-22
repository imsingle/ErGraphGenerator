package com.taobao.rhino;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.taobao.rhino.service.ERService;

public class App {

    public static void main(String[] args) throws IOException {
        help(args);
        System.out.println("开始生产ER图...");
        List<Path> inputPaths = fileCheckAndFormat(args);
        if (inputPaths.isEmpty()) {
            System.out.println("完成!");
            System.exit(1);
        }
        ERService erService = ERService.empty();
        for (Path inputPath : inputPaths) {
            String dbName = inputPath.getFileName().toString().replaceAll("\\.sql", "");
            String sql = new String(Files.readAllBytes(inputPath));
            if (StringUtils.isEmpty(sql)) {
                System.out.println("file " + inputPath.toString() + " is empty!");
                continue;
            }

            ERClient.generateMdj(erService, dbName, sql);
        }
        String outputFile = "ergroup";
        save(erService, outputFile);
        System.out.println("完成!");
    }

    public static void help(String[] args) {
        if (args.length == 0 || Arrays.asList("help", "-help", "--help", "h", "-h", "--h").contains(args[0])) {
            System.out.println("命令格式：java -jar xx.jar 1.sql 2.sql");
            System.exit(0);
        }
    }

    public static void save(ERService erService, String outputFile) {
        Path outputPath = ERSupport.getOutputPath(System.getProperty("user.dir"), outputFile);
        String projectNode = JSON.toJSONString(erService.getProjectNode(), SerializerFeature.PrettyFormat);
        try {
            ERService.save(outputPath, projectNode);
            System.out.println("输出文件：" + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 过滤空的和不存在的文件路径
     *
     * @param paths
     */
    public static List<Path> fileCheckAndFormat(String[] paths) {
        List<Path> list = new ArrayList<>();
        for (String path : paths) {
            if (StringUtils.isEmpty(path)) {
                continue;
            }
            if (!path.startsWith("/")) {
                path = System.getProperty("user.dir") + "/" + path;
            }
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("文件[" + path + "]未找到");
                continue;
            }
            list.add(Paths.get(file.toURI()));
        }
        return list;
    }
}
