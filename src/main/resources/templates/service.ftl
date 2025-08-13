package ${servicePackage};

import ${entityPackage}.${table.className};
import ${daoPackage}.${table.className}Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${table.tableComment!table.tableName} 服务层
 * 
 * @author ${author}
 * @date ${date}
 */
@Service
public class ${table.className}Service {
    
    @Autowired
    private ${table.className}Dao ${table.instanceName}Dao;
    
    /**
     * 查询所有记录
     */
    public List<${table.className}> findAll() {
        return ${table.instanceName}Dao.findAll();
    }
    
    /**
     * 根据ID查询记录
     */
    public ${table.className} findById(<#list table.columns as column><#if column.primaryKey>${column.javaType} ${column.propertyName}</#if></#list>) {
        return ${table.instanceName}Dao.findById(<#list table.columns as column><#if column.primaryKey>${column.propertyName}</#if></#list>);
    }
    
    /**
     * 保存记录
     */
    public boolean save(${table.className} ${table.instanceName}) {
        if (<#list table.columns as column><#if column.primaryKey>${table.instanceName}.${column.getterName}() == null</#if></#list>) {
            // 新增
            return ${table.instanceName}Dao.insert(${table.instanceName}) > 0;
        } else {
            // 更新
            return ${table.instanceName}Dao.update(${table.instanceName}) > 0;
        }
    }
    
    /**
     * 删除记录
     */
    public boolean deleteById(<#list table.columns as column><#if column.primaryKey>${column.javaType} ${column.propertyName}</#if></#list>) {
        return ${table.instanceName}Dao.deleteById(<#list table.columns as column><#if column.primaryKey>${column.propertyName}</#if></#list>) > 0;
    }
    
    /**
     * 分页查询
     */
    public List<${table.className}> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return ${table.instanceName}Dao.findByPage(offset, size);
    }
    
    /**
     * 统计总记录数
     */
    public long count() {
        return ${table.instanceName}Dao.count();
    }
}