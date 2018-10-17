package

<#if isDateFlag>
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@VersionAudit
@ModifyAudit
@Table(name = "${tableName}")
public class ${className} extends AuditDomain {
<#list columnList as column>

    /**
     * ${column.description?if_exists}
     */
    <#if column.fieldType == "Date">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    </#if>
    <#if column.description == "弹性域字段">
    @JsonIgnore
    </#if>
    private ${column.fieldType} ${column.fieldName};
</#list>

}
