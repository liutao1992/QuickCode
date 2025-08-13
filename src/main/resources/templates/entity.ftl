package ${entityPackage};

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

<#list table.columns as column>
<#if column.javaType?contains("java.time") || column.javaType?contains("java.math")>
import ${column.javaType};
</#if>
</#list>

/**
 * ${table.tableComment!table.tableName} 实体类
 * 
 * @author ${author}
 * @date ${date}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${table.className} {
    
<#list table.columns as column>
    /**
     * ${column.columnComment!column.columnName}
     */
    private ${column.javaType} ${column.propertyName};
    
</#list>
}