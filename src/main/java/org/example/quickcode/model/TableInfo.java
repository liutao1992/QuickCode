package org.example.quickcode.model;

import java.util.List;

public class TableInfo {
    private String tableName;
    private String tableComment;
    private List<ColumnInfo> columns;
    private String primaryKey;
    
    // 构造函数
    public TableInfo() {}
    
    public TableInfo(String tableName, String tableComment) {
        this.tableName = tableName;
        this.tableComment = tableComment;
    }
    
    // Getters and Setters
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTableComment() {
        return tableComment;
    }
    
    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
    
    public List<ColumnInfo> getColumns() {
        return columns;
    }
    
    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }
    
    public String getPrimaryKey() {
        return primaryKey;
    }
    
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    // 获取类名（首字母大写的驼峰命名）
    public String getClassName() {
        return toCamelCase(tableName, true);
    }
    
    // 获取实例名（首字母小写的驼峰命名）
    public String getInstanceName() {
        return toCamelCase(tableName, false);
    }
    
    private String toCamelCase(String str, boolean capitalizeFirst) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        String[] parts = str.split("_");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].toLowerCase();
            if (i == 0 && !capitalizeFirst) {
                result.append(part);
            } else {
                result.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    result.append(part.substring(1));
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * 获取主键属性名（Java命名）
     */
    public String getPrimaryKeyProperty() {
        for (ColumnInfo column : columns) {
            if (column.isPrimaryKey()) {
                return column.getPropertyName();
            }
        }
        return "id"; // 默认返回id
    }
}