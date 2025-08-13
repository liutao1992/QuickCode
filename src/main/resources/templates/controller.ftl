package ${controllerPackage};

import ${entityPackage}.${table.className};
import ${servicePackage}.${table.className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${table.tableComment!table.tableName} 控制器
 * 
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequestMapping("/${table.instanceName}")
@CrossOrigin
public class ${table.className}Controller {
    
    @Autowired
    private ${table.className}Service ${table.instanceName}Service;
    
    /**
     * 查询所有记录
     */
    @GetMapping
    public ResponseEntity<List<${table.className}>> findAll() {
        List<${table.className}> list = ${table.instanceName}Service.findAll();
        return ResponseEntity.ok(list);
    }
    
    /**
     * 根据ID查询记录
     */
    @GetMapping("/{id}")
    public ResponseEntity<${table.className}> findById(@PathVariable <#list table.columns as column><#if column.primaryKey>${column.javaType} id</#if></#list>) {
        ${table.className} ${table.instanceName} = ${table.instanceName}Service.findById(id);
        if (${table.instanceName} != null) {
            return ResponseEntity.ok(${table.instanceName});
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 保存记录
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody ${table.className} ${table.instanceName}) {
        Map<String, Object> result = new HashMap<>();
        boolean success = ${table.instanceName}Service.save(${table.instanceName});
        
        if (success) {
            result.put("success", true);
            result.put("message", "保存成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "保存失败");
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 更新记录
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable <#list table.columns as column><#if column.primaryKey>${column.javaType} id</#if></#list>, @RequestBody ${table.className} ${table.instanceName}) {
        Map<String, Object> result = new HashMap<>();
        <#list table.columns as column><#if column.primaryKey>${table.instanceName}.${column.setterName}(id);</#if></#list>
        boolean success = ${table.instanceName}Service.save(${table.instanceName});
        
        if (success) {
            result.put("success", true);
            result.put("message", "更新成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "更新失败");
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 删除记录
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable <#list table.columns as column><#if column.primaryKey>${column.javaType} id</#if></#list>) {
        Map<String, Object> result = new HashMap<>();
        boolean success = ${table.instanceName}Service.deleteById(id);
        
        if (success) {
            result.put("success", true);
            result.put("message", "删除成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "删除失败");
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> findByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<${table.className}> list = ${table.instanceName}Service.findByPage(page, size);
        long total = ${table.instanceName}Service.count();
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return ResponseEntity.ok(result);
    }
}