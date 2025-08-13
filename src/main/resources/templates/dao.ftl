package ${daoPackage};

import ${entityPackage}.${table.className};
import com.github.chengyuxing.sql.BakiDao;
import com.github.chengyuxing.sql.page.PagedResource;
import com.github.chengyuxing.common.DataRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

/**
 * ${table.tableComment!table.tableName} DAO层 - 使用Baki方式
 * 
 * @author ${author}
 * @date ${date}
 */
@Repository
public class ${table.className}Dao {
    
    @Autowired
    private BakiDao bakiDao;
    
    /**
     * 查询所有记录
     */
    public List<${table.className}> findAll() {
        return bakiDao.query("&${table.instanceName}.findAll")
                .listEntity(${table.className}.class);
    }
    
    /**
     * 根据ID查询记录
     */
    public Optional<${table.className}> findById(<#list table.columns as column><#if column.primaryKey>${column.javaType} ${column.propertyName}</#if></#list>) {
        return bakiDao.query("&${table.instanceName}.findById")
                .arg("${table.primaryKeyProperty}", <#list table.columns as column><#if column.primaryKey>${column.propertyName}</#if></#list>)
                .optionalEntity(${table.className}.class);
    }
    
    /**
     * 插入记录
     */
    public int insert(${table.className} ${table.instanceName}) {
        Map<String, Object> params = new HashMap<>();
<#list table.columns as column>
<#if !column.autoIncrement>
        params.put("${column.propertyName}", ${table.instanceName}.${column.getterName}());
</#if>
</#list>
        
        return bakiDao.query("&${table.instanceName}.insert")
                .args(params)
                .execute();
    }
    
    /**
     * 更新记录
     */
    public int update(${table.className} ${table.instanceName}) {
        Map<String, Object> params = new HashMap<>();
<#list table.columns as column>
        params.put("${column.propertyName}", ${table.instanceName}.${column.getterName}());
</#list>
        
        return bakiDao.query("&${table.instanceName}.update")
                .args(params)
                .execute();
    }
    
    /**
     * 删除记录
     */
    public int deleteById(<#list table.columns as column><#if column.primaryKey>${column.javaType} ${column.propertyName}</#if></#list>) {
        return bakiDao.query("&${table.instanceName}.deleteById")
                .arg("${table.primaryKeyProperty}", <#list table.columns as column><#if column.primaryKey>${column.propertyName}</#if></#list>)
                .execute();
    }
    
    /**
     * 分页查询
     */
    public PagedResource<${table.className}> findByPage(int page, int size) {
        return bakiDao.query("&${table.instanceName}.findByPage")
                .pageable(page, size)
                .collect(${table.className}.class);
    }
    
    /**
     * 统计总记录数
     */
    public Long count() {
        return bakiDao.query("&${table.instanceName}.count")
                .queryForObject(Long.class);
    }
    
    /**
     * 根据条件查询
     */
    public List<${table.className}> findByCondition(Map<String, Object> conditions) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ${table.tableName} WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        
        conditions.forEach((key, value) -> {
            if (value != null) {
                sql.append(" AND ").append(key).append(" = :").append(key);
                params.put(key, value);
            }
        });
        
        return bakiDao.query(sql.toString())
                .args(params)
                .listEntity(${table.className}.class);
    }
    
    /**
     * 批量插入
     */
    public int[] batchInsert(List<${table.className}> ${table.instanceName}List) {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        
        for (${table.className} ${table.instanceName} : ${table.instanceName}List) {
            Map<String, Object> params = new HashMap<>();
<#list table.columns as column>
<#if !column.autoIncrement>
            params.put("${column.propertyName}", ${table.instanceName}.${column.getterName}());
</#if>
</#list>
            paramsList.add(params);
        }
        
        return bakiDao.query("&${table.instanceName}.insert")
                .batch(paramsList)
                .executeBatch();
    }
}