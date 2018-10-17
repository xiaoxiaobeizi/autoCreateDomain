package com.my.utils;

/**
 * description:
 *
 * @author siao.qian@hand-china.com
 * @date 18/10/17 14:46
 * lastUpdateBy: siao.qian@hand-china.com
 * lastUpdateDate: 18/10/17
 */
public class ChangeName {

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */
    public static String UnderlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */
    public static String HumpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /***
     * 首字母大写
     *
     * @param para
     * @return  首字母大写的字符串
     */
    public static String CapitalizedFirst(String para) {
        if (Character.isUpperCase(para.charAt(0))) {
            return para;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(para.charAt(0))).append(para.substring(1)).toString();
        }
    }

}
