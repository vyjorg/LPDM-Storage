package com.lpdm.msstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Configuration
@EnableWebMvc
public class FileConfig implements WebMvcConfigurer {

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(30*1024*1024);
        multipartResolver.setMaxUploadSizePerFile(10*1024*1024);
        return multipartResolver;
    }
}
