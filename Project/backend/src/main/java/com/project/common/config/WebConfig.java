package com.project.common.config;  

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:}")
    private String bannerUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 포토게시판 (로컬 개발용)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Image/");

        // 배너 (로컬 개발용)
        registry.addResourceHandler("/banner-uploads/**")
                .addResourceLocations("file:///C:/banner-uploads/");

        // 서버용 배너 정적 리소스 매핑 (/api/files/{파일명})
        if (bannerUploadDir != null && !bannerUploadDir.isBlank()) {
            String location = Paths.get(bannerUploadDir)
                    .toAbsolutePath()
                    .toUri()
                    .toString();   // 예: file:/home/ubuntu/frontend/public/DATA/banner/

            registry.addResourceHandler("/files/**")
                    .addResourceLocations(location);
        }
    }
}
