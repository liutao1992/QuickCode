package org.example.quickcode.controller;

import org.example.quickcode.model.TableInfo;
import org.example.quickcode.service.CodeGeneratorService;
import org.example.quickcode.service.DatabaseAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器控制器
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController {
    
    @Autowired
    private DatabaseAnalyzer databaseAnalyzer;
    
    @Autowired
    private CodeGeneratorService codeGeneratorService;
    
    /**
     * 首页
     */
    @GetMapping
    public String index(Model model) {
        try {
            List<TableInfo> tables = databaseAnalyzer.getAllTables();
            model.addAttribute("tables", tables);
        } catch (Exception e) {
            model.addAttribute("error", "获取数据库表信息失败: " + e.getMessage());
        }
        return "generator/index";
    }
    
    /**
     * 获取表信息
     */
    @GetMapping("/table/{tableName}")
    @ResponseBody
    public Map<String, Object> getTableInfo(@PathVariable String tableName) {
        Map<String, Object> result = new HashMap<>();
        try {
            TableInfo tableInfo = databaseAnalyzer.getTableInfo(tableName);
            result.put("success", true);
            result.put("data", tableInfo);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取表信息失败: " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取表信息（POST，兼容前端调用）
     */
    @PostMapping("/table")
    @ResponseBody
    public Map<String, Object> getTableInfoPost(@RequestParam String tableName) {
        Map<String, Object> result = new HashMap<>();
        try {
            TableInfo tableInfo = databaseAnalyzer.getTableInfo(tableName);
            result.put("success", true);
            result.put("table", tableInfo);
            result.put("columns", tableInfo.getColumns());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取表信息失败: " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 生成代码
     */
    @PostMapping("/generate")
    @ResponseBody
    public Map<String, Object> generateCode(@RequestParam String tableName) {
        Map<String, Object> result = new HashMap<>();
        try {
            TableInfo tableInfo = databaseAnalyzer.getTableInfo(tableName);
            codeGeneratorService.generateAllCode(tableInfo);
            
            result.put("success", true);
            result.put("message", "代码生成成功！");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "代码生成失败: " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 预览代码
     */
    @PostMapping("/preview")
    @ResponseBody
    public Map<String, Object> previewCode(@RequestParam String tableName, @RequestParam String type) {
        Map<String, Object> result = new HashMap<>();
        try {
            TableInfo tableInfo = databaseAnalyzer.getTableInfo(tableName);
            String code = "";
            
            switch (type.toLowerCase()) {
                case "entity":
                    code = codeGeneratorService.generateEntity(tableInfo);
                    break;
                case "dao":
                    code = codeGeneratorService.generateDao(tableInfo);
                    break;
                case "service":
                    code = codeGeneratorService.generateService(tableInfo);
                    break;
                case "controller":
                    code = codeGeneratorService.generateController(tableInfo);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的代码类型: " + type);
            }
            
            result.put("success", true);
            result.put("code", code);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "代码预览失败: " + e.getMessage());
        }
        return result;
    }
}