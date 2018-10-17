package com.my.utils;

import com.my.Bean.DomainBean;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.my.utils.ChangeName.CapitalizedFirst;
import static com.my.utils.ChangeName.UnderlineToHump;

/**
 * description:
 *
 * @author siao.qian@hand-china.com
 * @date 18/10/17 09:58
 * lastUpdateBy: siao.qian@hand-china.com
 * lastUpdateDate: 18/10/17
 */
public class AutoCreateDomain {
    public static void createDomain(String tableName) {
        //字段类型的MAP
        Map<String,String> fieldTypes = new HashMap<String, String>(10);
        fieldTypes.put("BIGINT","Long");
        fieldTypes.put("BIGINT UNSIGNED","Long");
        fieldTypes.put("VARCHAR","String");
        fieldTypes.put("TEXT","String");
        fieldTypes.put("DATETIME","Date");
        fieldTypes.put("TINYINT","Boolean");

        Set<String> who = new HashSet<String>(5);
        who.add("object_version_number");
        who.add("last_update_date");
        who.add("last_updated_by");
        who.add("creation_date");
        who.add("created_by");

        try {
            //创建字段的属性
            List<DomainBean> columnList = new ArrayList<DomainBean>();
            List<Map<String,String>> dataBaseSource = CatchDataBase.getTableInfo(tableName);
            System.out.println(dataBaseSource);
            System.out.println();

            Boolean isDateFlag = false;
            String className = UnderlineToHump(tableName);
            className = CapitalizedFirst(className);

            for (Map<String,String> column : dataBaseSource){
                if (null == column.get("dbType")){
                    column.put("name","VARCHAR");
                }
                if ("Date".equals(fieldTypes.get(column.get("dbType")))){
                    isDateFlag = true;
                }
                if (who.add(column.get("code"))){
                    columnList.add(new DomainBean(column.get("name"),
                            fieldTypes.get(column.get("dbType")),
                            UnderlineToHump(column.get("code"))));
                }
            }
            System.out.println(columnList);
            System.out.println();

            // 创建插值的map
            Map<String, Object> map = new HashMap<String, Object>(20);
            map.put("tableName", tableName);
            map.put("columnList", columnList);
            map.put("isDateFlag", isDateFlag);
            map.put("className", className);


            // 创建一个模板对象
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            Template t = configuration.getTemplate("src/main/resources/ftl/domain.ftl");
            File outFile = new File("D:/outFiles/" + className + ".java");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));

            // 执行插值，并输出到指定的输出流中
            t.process(map, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

