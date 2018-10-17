package com.my.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * description:
 *
 * @author siao.qian@hand-china.com
 * @date 18/10/17 11:23
 * lastUpdateBy: siao.qian@hand-china.com
 * lastUpdateDate: 18/10/17
 */
public class CatchDataBase {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://rm-uf6ceu5c4lr88z6vdvo.mysql.rds.aliyuncs.com:3306/itsm_service?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=yes";
    private static final String user = "hitom";
    private static final String pwd = "Hitom123";


    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     *
     * @param table 表名
     * @return Map集合
     */
    public static List getTableInfo(String table) {
        List result = new ArrayList();

        Connection conn = null;
        DatabaseMetaData dbmd = null;

        try {
            conn = getConnections(driver, url, user, pwd);

            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[]{"TABLE"});

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName);

                if (tableName.equals(table)) {
                    ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");

                    while (rs.next()) {
                        //System.out.println("字段名："+rs.getString("COLUMN_NAME")+"--字段注释："+rs.getString("REMARKS")+"--字段数据类型："+rs.getString("TYPE_NAME"));
                        Map map = new HashMap();
                        String colName = rs.getString("COLUMN_NAME");
                        map.put("code", colName);

                        String remarks = rs.getString("REMARKS");
                        if (remarks == null || remarks.equals("")) {
                            remarks = colName;
                        }
                        map.put("name", remarks);

                        String dbType = rs.getString("TYPE_NAME");
                        map.put("dbType", dbType);

                        map.put("valueType", changeDbType(dbType));
                        result.add(map);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        if ("VARCHAR".equals(dbType) || "VARCHAR2".equals(dbType) || "CHAR".equals(dbType)) {
            return "1";
        } else if ("NUMBER".equals(dbType) || "DECIMAL".equals(dbType)) {
            return "4";
        } else if ("INT".equals(dbType) || "SMALLINT".equals(dbType) || "INTEGER".equals(dbType)) {
            return "2";
        } else if ("BIGINT".equals(dbType)) {
            return "6";
        } else if ("DATETIME".equals(dbType) || "TIMESTAMP".equals(dbType) || "DATE".equals(dbType)) {
            return "7";
        } else {
            return "1";
        }
    }

    //获取连接
    private static Connection getConnections(String driver, String url, String user, String pwd) throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", user);
            props.put("password", pwd);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    //其他数据库不需要这个方法 oracle和db2需要
    private static String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase().toString();

    }
}
