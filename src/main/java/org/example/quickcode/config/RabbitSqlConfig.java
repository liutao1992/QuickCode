package org.example.quickcode.config;

import com.github.chengyuxing.sql.BakiDao;
import com.github.chengyuxing.sql.XQLFileManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.File;

/**
 * Rabbit SQL Configuration
 * 
 * @author ${author}
 * @date ${date}
 */
@Configuration
public class RabbitSqlConfig {
    
    @Bean
    public XQLFileManager xqlFileManager() {
        XQLFileManager xqlFileManager = new XQLFileManager();
        
        // Check if sql directory exists, if not create it
        try {
            ClassPathResource resource = new ClassPathResource("sql/");
            if (resource.exists()) {
                xqlFileManager.add("classpath:sql/");
            }
        } catch (Exception e) {
            // SQL directory doesn't exist, skip adding it for now
            System.out.println("SQL directory not found, skipping XQL file loading");
        }
        
        return xqlFileManager;
    }
    
    @Bean
    public BakiDao bakiDao(DataSource dataSource, XQLFileManager xqlFileManager) {
        BakiDao bakiDao = new BakiDao(dataSource);
        bakiDao.setXqlFileManager(xqlFileManager);
        return bakiDao;
    }
}