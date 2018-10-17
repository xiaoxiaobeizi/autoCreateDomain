package com.my.Bean;

/**
 * description:
 *
 * @author siao.qian@hand-china.com
 * @date 18/10/17 11:13
 * lastUpdateBy: siao.qian@hand-china.com
 * lastUpdateDate: 18/10/17
 */
public class DomainBean {

    private String description;
    private String fieldType;
    private String fieldName;

    public DomainBean(String description, String fieldType, String fieldName) {
        this.description = description;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "{" +
                "description='" + description + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
