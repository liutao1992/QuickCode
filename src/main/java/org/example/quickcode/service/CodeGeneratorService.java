package org.example.quickcode.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.quickcode.config.CodeGeneratorConfig;
import org.example.quickcode.model.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class CodeGeneratorService {
    
    @Autowired
    private CodeGeneratorConfig config;
    
    @Autowired
    private Configuration freemarkerConfig;
    
    /**
     * 生成所有CRUD代码
     */
    public void generateAllCode(TableInfo tableInfo) throws Exception {
        generateEntity(tableInfo);
        generateDao(tableInfo);
        generateSqlFile(tableInfo);  // 新增SQL文件生成
        generateService(tableInfo);
        generateController(tableInfo);
    }
    
    /**
     * 生成SQL文件
     */
    public String generateSqlFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> dataModel = createDataModel(tableInfo);
        
        Template template = freemarkerConfig.getTemplate("sql.ftl");
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        
        String content = writer.toString();
        
        // 写入SQL文件
        String sqlDir = config.getOutputPath() + "/src/main/resources/sql/";
        createDirectoryIfNotExists(sqlDir);
        
        String fileName = tableInfo.getInstanceName() + ".sql";
        writeToFile(sqlDir + fileName, content);
        
        return content;
    }
    
    /**
     * 生成Entity类
     */
    public String generateEntity(TableInfo tableInfo) throws IOException, TemplateException {
        Map<String, Object> dataModel = createDataModel(tableInfo);
        
        Template template = freemarkerConfig.getTemplate("entity.ftl");
        String code = processTemplate(template, dataModel);
        
        // 写入文件
        String fileName = tableInfo.getClassName() + ".java";
        String packagePath = config.getEntityPackage().replace(".", "/");
        writeToFile(code, packagePath, fileName);
        
        return code;
    }
    
    /**
     * 生成DAO接口
     */
    public String generateDao(TableInfo tableInfo) throws IOException, TemplateException {
        Map<String, Object> dataModel = createDataModel(tableInfo);
        
        Template template = freemarkerConfig.getTemplate("dao.ftl");
        String code = processTemplate(template, dataModel);
        
        // 写入文件
        String fileName = tableInfo.getClassName() + "Dao.java";
        String packagePath = config.getDaoPackage().replace(".", "/");
        writeToFile(code, packagePath, fileName);
        
        return code;
    }
    
    /**
     * 生成Service类
     */
    public String generateService(TableInfo tableInfo) throws IOException, TemplateException {
        Map<String, Object> dataModel = createDataModel(tableInfo);
        
        Template template = freemarkerConfig.getTemplate("service.ftl");
        String code = processTemplate(template, dataModel);
        
        
        // 写入文件
        String fileName = tableInfo.getClassName() + "Service.java";
        String packagePath = config.getServicePackage().replace(".", "/");
        writeToFile(code, packagePath, fileName);
        
        return code;
    }
    
    /**
     * 生成Controller类
     */
    public String generateController(TableInfo tableInfo) throws IOException, TemplateException {
        Map<String, Object> dataModel = createDataModel(tableInfo);
        
        Template template = freemarkerConfig.getTemplate("controller.ftl");
        String code = processTemplate(template, dataModel);
        
        // 写入文件
        String fileName = tableInfo.getClassName() + "Controller.java";
        String packagePath = config.getControllerPackage().replace(".", "/");
        writeToFile(code, packagePath, fileName);
        
        return code;
    }
    
    /**
     * 创建模板数据模型
     */
    private Map<String, Object> createDataModel(TableInfo tableInfo) {
        Map<String, Object> dataModel = new HashMap<>();
        
        dataModel.put("table", tableInfo);
        dataModel.put("entityPackage", config.getEntityPackage());
        dataModel.put("daoPackage", config.getDaoPackage());
        dataModel.put("servicePackage", config.getServicePackage());
        dataModel.put("controllerPackage", config.getControllerPackage());
        dataModel.put("author", config.getAuthor());
        dataModel.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return dataModel;
    }
    
    /**
     * 处理模板
     */
    private String processTemplate(Template template, Map<String, Object> dataModel) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }
    
    /**
     * 写入文件
     */
    private void writeToFile(String content, String packagePath, String fileName) throws IOException {
        String fullPath = config.getOutputPath() + "/" + packagePath;
        File directory = new File(fullPath);
        
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        File file = new File(directory, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
    
    /**
     * 创建目录（如果不存在）
     */
    private void createDirectoryIfNotExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    /**
     * 写入文件（重载方法）
     */
    private void writeToFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}