package com.example.generated.controller;

import com.example.generated.entity.ZhagXcSxtxx;
import com.example.generated.service.ZhagXcSxtxxService;
import com.github.chengyuxing.common.DataRow;
import com.github.chengyuxing.sql.PagedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 摄像头基本信息 控制器
 * 
 * @author QuickCode Generator
 * @date 2025-08-13 15:15:18
 */
@RestController
@RequestMapping("/zhagXcSxtxx")
@CrossOrigin
public class ZhagXcSxtxxController {
    
    @Autowired
    private ZhagXcSxtxxService zhagXcSxtxxService;
    
    /**
     * 查询所有记录
     */
    @GetMapping
    public ResponseEntity<List<ZhagXcSxtxx>> findAll() {
        List<ZhagXcSxtxx> list = zhagXcSxtxxService.findAll();
        return ResponseEntity.ok(list);
    }
    
    /**
     * 根据ID查询记录
     */
    @GetMapping("/{id}")
    public ResponseEntity<ZhagXcSxtxx> findById(@PathVariable String id) {
        ZhagXcSxtxx zhagXcSxtxx = zhagXcSxtxxService.findById(id);
        if (zhagXcSxtxx != null) {
            return ResponseEntity.ok(zhagXcSxtxx);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 保存记录
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody ZhagXcSxtxx zhagXcSxtxx) {
        Map<String, Object> result = new HashMap<>();
        boolean success = zhagXcSxtxxService.save(zhagXcSxtxx);
        
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
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody ZhagXcSxtxx zhagXcSxtxx) {
        Map<String, Object> result = new HashMap<>();
        zhagXcSxtxx.setId(id);
        boolean success = zhagXcSxtxxService.save(zhagXcSxtxx);
        
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
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = zhagXcSxtxxService.deleteById(id);
        
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
    public ResponseEntity<?> findByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        PagedResource<DataRow> list = zhagXcSxtxxService.findByPage(page, size);

        return ResponseEntity.ok(list);
    }
}