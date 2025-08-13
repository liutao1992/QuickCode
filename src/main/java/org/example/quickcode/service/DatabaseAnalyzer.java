package org.example.quickcode.service;

import org.example.quickcode.model.ColumnInfo;
import org.example.quickcode.model.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseAnalyzer {
    
    @Autowired
    private DataSource dataSource;
    
    private static final Map<String, String> TYPE_MAPPING = new HashMap<>();
    
    static {
        // MySQL类型映射
        TYPE_MAPPING.put("TINYINT", "Integer");
        TYPE_MAPPING.put("SMALLINT", "Integer");
        TYPE_MAPPING.put("MEDIUMINT", "Integer");
        TYPE_MAPPING.put("INT", "Integer");
        TYPE_MAPPING.put("INTEGER", "Integer");
        TYPE_MAPPING.put("BIGINT", "Long");
        TYPE_MAPPING.put("FLOAT", "Float");
        TYPE_MAPPING.put("DOUBLE", "Double");
        TYPE_MAPPING.put("DECIMAL", "java.math.BigDecimal");
        TYPE_MAPPING.put("NUMERIC", "java.math.BigDecimal");
        TYPE_MAPPING.put("CHAR", "String");
        TYPE_MAPPING.put("VARCHAR", "String");
        TYPE_MAPPING.put("TEXT", "String");
        TYPE_MAPPING.put("LONGTEXT", "String");
        TYPE_MAPPING.put("DATE", "java.time.LocalDate");
        TYPE_MAPPING.put("TIME", "java.time.LocalTime");
        TYPE_MAPPING.put("DATETIME", "java.time.LocalDateTime");
        TYPE_MAPPING.put("TIMESTAMP", "java.time.LocalDateTime");
        TYPE_MAPPING.put("BOOLEAN", "Boolean");
        TYPE_MAPPING.put("TINYINT(1)", "Boolean");
    }
    
    /**
     * 获取数据库中所有表的信息
     */
    public List<TableInfo> getAllTables() throws SQLException {
        List<TableInfo> tables = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            
            // 获取所有表
            try (ResultSet tableResultSet = metaData.getTables(catalog, null, null, new String[]{"TABLE"})) {
                while (tableResultSet.next()) {
                    String tableName = tableResultSet.getString("TABLE_NAME");
                    String tableComment = tableResultSet.getString("REMARKS");
                    
                    TableInfo tableInfo = new TableInfo(tableName, tableComment);
                    tableInfo.setColumns(getTableColumns(connection, tableName));
                    tableInfo.setPrimaryKey(getPrimaryKey(connection, tableName));
                    
                    tables.add(tableInfo);
                }
            }
        }
        
        return tables;
    }
    
    /**
     * 获取指定表的信息
     */
    public TableInfo getTableInfo(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            
            // 获取表信息
            try (ResultSet tableResultSet = metaData.getTables(catalog, null, tableName, new String[]{"TABLE"})) {
                if (tableResultSet.next()) {
                    String tableComment = tableResultSet.getString("REMARKS");
                    
                    TableInfo tableInfo = new TableInfo(tableName, tableComment);
                    tableInfo.setColumns(getTableColumns(connection, tableName));
                    tableInfo.setPrimaryKey(getPrimaryKey(connection, tableName));
                    
                    return tableInfo;
                }
            }
        }
        
        throw new SQLException("Table not found: " + tableName);
    }
    
    /**
     * 获取表的列信息
     */
    private List<ColumnInfo> getTableColumns(Connection connection, String tableName) throws SQLException {
        List<ColumnInfo> columns = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        String catalog = connection.getCatalog();
        
        try (ResultSet columnResultSet = metaData.getColumns(catalog, null, tableName, null)) {
            while (columnResultSet.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String columnType = columnResultSet.getString("TYPE_NAME");
                int columnSize = columnResultSet.getInt("COLUMN_SIZE");
                String columnComment = columnResultSet.getString("REMARKS");
                boolean nullable = columnResultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                String defaultValue = columnResultSet.getString("COLUMN_DEF");
                boolean autoIncrement = "YES".equals(columnResultSet.getString("IS_AUTOINCREMENT"));
                
                columnInfo.setColumnName(columnName);
                columnInfo.setColumnType(columnType);
                columnInfo.setJavaType(mapToJavaType(columnType));
                columnInfo.setColumnSize(columnSize);
                columnInfo.setColumnComment(columnComment);
                columnInfo.setNullable(nullable);
                columnInfo.setDefaultValue(defaultValue);
                columnInfo.setAutoIncrement(autoIncrement);
                
                columns.add(columnInfo);
            }
        }
        
        // 设置主键信息
        setPrimaryKeyInfo(connection, tableName, columns);
        
        return columns;
    }
    
    /**
     * 获取主键字段名
     */
    private String getPrimaryKey(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String catalog = connection.getCatalog();
        
        try (ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(catalog, null, tableName)) {
            if (primaryKeyResultSet.next()) {
                return primaryKeyResultSet.getString("COLUMN_NAME");
            }
        }
        
        return null;
    }
    
    /**
     * 设置主键信息
     */
    private void setPrimaryKeyInfo(Connection connection, String tableName, List<ColumnInfo> columns) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String catalog = connection.getCatalog();
        
        try (ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(catalog, null, tableName)) {
            while (primaryKeyResultSet.next()) {
                String primaryKeyColumn = primaryKeyResultSet.getString("COLUMN_NAME");
                
                for (ColumnInfo column : columns) {
                    if (column.getColumnName().equals(primaryKeyColumn)) {
                        column.setPrimaryKey(true);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * 数据库类型映射到Java类型
     */
    private String mapToJavaType(String dbType) {
        String upperType = dbType.toUpperCase();
        
        // 处理带长度的类型，如VARCHAR(255)
        if (upperType.contains("(")) {
            upperType = upperType.substring(0, upperType.indexOf("("));
        }
        
        return TYPE_MAPPING.getOrDefault(upperType, "String");
    }
}