package com.taobao.rhino;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
public class IdbConstant {
    public static String IDB_HOST = "idb4.alibaba-inc.com";
    /**
     * 获取DB列表
     */
    public static String IDB_DATABASE_META_INFO = "https://" + IDB_HOST + "/datasource/my/database";
    /**
     * 逻辑库下的表
     */
    public static String IDB_LOGIC_TABLE_META_INFO = "https://" + IDB_HOST + "/logic/table/list";
    /**
     * 逻辑表下的字段
     */
    public static String IDB_LOGIC_COLUMN_META_INFO = "https://" + IDB_HOST + "/logic/table/columns";

    /**
     * 物理库下的表
     */
    public static String IDB_PHYSICAL_TABLE_META_INFO = "https://" + IDB_HOST + "/meta/table/db/list";
    /**
     * 物理表下的字段
     */
    public static String IDB_PHYSICAL_COLUMN_META_INFO = "https://" + IDB_HOST + "/meta/table/columns";

    /**
     * 画布最大高
     */
    public static Double VIEW_CANVAS_MAX_TOP = 1300.0;

    /**
     * 画布最大宽
     */
    public static Double VIEW_CANVAS_MAX_LEFT = 1800.0;

    /**
     * 画布默认以后放20个
     */
    public static Integer VIEW_CANVAS_MAX_LINE_COUNT = 20;

    /**
     * 画布默认行距
     */
    public static Integer VIEW_CANVAS_MAX_SPACING = 150;
}
