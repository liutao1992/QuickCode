package org.example.quickcode.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {
    
    @Bean
    public Configuration freemarkerConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        
        // 设置模板加载路径
        cfg.setDirectoryForTemplateLoading(
            new ClassPathResource("templates/").getFile()
        );
        
        // 设置默认编码
        cfg.setDefaultEncoding("UTF-8");
        
        // 设置模板异常处理器
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        // 不要在生产环境中使用
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        
        return cfg;
    }
}