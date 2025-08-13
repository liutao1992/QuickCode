package org.example.quickcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "code.generator")
public class CodeGeneratorConfig {
    
    private String basePackage = "com.example.generated";
    private String outputPath = "src/main/java";
    private String author = "QuickCode Generator";
    private String templatePath = "templates/";
    
    // Getters and Setters
    public String getBasePackage() {
        return basePackage;
    }
    
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    
    public String getOutputPath() {
        return outputPath;
    }
    
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getTemplatePath() {
        return templatePath;
    }
    
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
    
    // 获取各层包名
    public String getEntityPackage() {
        return basePackage + ".entity";
    }
    
    public String getDaoPackage() {
        return basePackage + ".dao";
    }
    
    public String getServicePackage() {
        return basePackage + ".service";
    }
    
    public String getControllerPackage() {
        return basePackage + ".controller";
    }
}