package com.btc.snow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/UploadImg/**")
                .addResourceLocations("file:///c://snow//user//meeting//study//")
                .addResourceLocations("file:///c://snow//user//meeting//meal//");

    }


}
