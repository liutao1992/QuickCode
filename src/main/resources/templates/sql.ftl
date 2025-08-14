/*[findAll]*/
SELECT 
<#list table.columns as column>
    ${column.columnName}<#if column_has_next>,</#if>
</#list>
FROM ${table.tableName};

/*[findById]*/
SELECT 
<#list table.columns as column>
    ${column.columnName}<#if column_has_next>,</#if>
</#list>
FROM ${table.tableName} WHERE ${table.primaryKey} = :${table.primaryKeyProperty};

/*[insert]*/
INSERT INTO ${table.tableName} (
<#list table.columns as column>
<#if !column.autoIncrement>
    ${column.columnName}<#if column_has_next && !isLastNonAutoIncrement(column, table.columns)>,</#if>
</#if>
</#list>
) VALUES (
<#list table.columns as column>
<#if !column.autoIncrement>
    :${column.propertyName}<#if column_has_next && !isLastNonAutoIncrement(column, table.columns)>,</#if>
</#if>
</#list>
);

/*[update]*/
UPDATE ${table.tableName} SET
<#list table.columns as column>
<#if !column.primaryKey && !column.autoIncrement>
    ${column.columnName} = :${column.propertyName}<#if column_has_next && !isLastNonPrimaryKey(column, table.columns)>,</#if>
</#if>
</#list>
WHERE ${table.primaryKey} = :${table.primaryKeyProperty};

/*[deleteById]*/
DELETE FROM ${table.tableName} WHERE ${table.primaryKey} = :${table.primaryKeyProperty};

/*[findByPage]*/
SELECT 
<#list table.columns as column>
    ${column.columnName}<#if column_has_next>,</#if>
</#list>
FROM ${table.tableName} LIMIT :limit OFFSET :offset;

/*[count]*/
SELECT COUNT(*) FROM ${table.tableName};

<#function isLastNonAutoIncrement column columns>
    <#local found = false>
    <#list columns as col>
        <#if col.columnName == column.columnName>
            <#local found = true>
        <#elseif found && !col.autoIncrement>
            <#return false>
        </#if>
    </#list>
    <#return true>
</#function>

<#function isLastNonPrimaryKey column columns>
    <#local found = false>
    <#list columns as col>
        <#if col.columnName == column.columnName>
            <#local found = true>
        <#elseif found && !col.primaryKey && !col.autoIncrement>
            <#return false>
        </#if>
    </#list>
    <#return true>
</#function>