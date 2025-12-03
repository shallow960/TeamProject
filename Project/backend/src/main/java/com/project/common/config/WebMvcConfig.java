package com.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-imgbbs}")
    private String imgBbsUploadDir;    // ../frontend/public/DATA/bbs/imgBbs

    @Value("${file.upload-norbbs}")
    private String norBbsUploadDir;    // ../frontend/public/DATA/bbs/norBbs

    @Value("${file.upload-quesbbs}")
    private String quesBbsUploadDir;   // ../frontend/public/DATA/bbs/quesBbs

    @Value("${file.upload-sumnel}")
    private String thumbnailUploadDir; // ../frontend/public/DATA/bbs/thumbnail

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 포토 게시판 원본 이미지
        registry.addResourceHandler("/DATA/bbs/imgBbs/**")
                .addResourceLocations("file:" + imgBbsUploadDir + "/");

        // 일반 게시판 첨부
        registry.addResourceHandler("/DATA/bbs/norBbs/**")
                .addResourceLocations("file:" + norBbsUploadDir + "/");

        // QnA/FAQ 첨부
        registry.addResourceHandler("/DATA/bbs/quesBbs/**")
                .addResourceLocations("file:" + quesBbsUploadDir + "/");

        // 썸네일
        registry.addResourceHandler("/DATA/bbs/thumbnail/**")
                .addResourceLocations("file:" + thumbnailUploadDir + "/");
    }
}
