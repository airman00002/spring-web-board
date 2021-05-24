package com.blogbackend.blogbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/blog/*").allowedMethods("*"); //*addMapping("/**") ทุก path
		registry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE")
		.allowedHeaders("*")
		.allowedOrigins("http://localhost:4200");
		
	}
	
}
