package org.example.quickcode.model;

public class ColumnInfo {
    private String columnName;
    private String columnType;
    private String javaType;
    private String columnComment;
    private boolean nullable;
    private boolean primaryKey;
    private boolean autoIncrement;
    private String defaultValue;
    private Integer columnSize;
    
    // 构造函数
    public ColumnInfo() {}
    
    public ColumnInfo(String columnName, String columnType, String javaType) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.javaType = javaType;
    }
    
    // Getters and Setters
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public String getColumnType() {
        return columnType;
    }
    
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    
    public String getJavaType() {
        return javaType;
    }
    
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
    
    public String getColumnComment() {
        return columnComment;
    }
    
    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
    
    public boolean isNullable() {
        return nullable;
    }
    
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    
    public boolean isPrimaryKey() {
        return primaryKey;
    }
    
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public boolean isAutoIncrement() {
        return autoIncrement;
    }
    
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public Integer getColumnSize() {
        return columnSize;
    }
    
    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }
    
    // 获取Java属性名（驼峰命名）
    public String getPropertyName() {
        return toCamelCase(columnName);
    }
    
    // 获取Getter方法名
    public String getGetterName() {
        String propertyName = getPropertyName();
        return "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }
    
    // 获取Setter方法名
    public String getSetterName() {
        String propertyName = getPropertyName();
        return "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
    }
    
    private String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        String[] parts = str.split("_");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].toLowerCase();
            result.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                result.append(part.substring(1));
            }
        }
        
        return result.toString();
    }
}