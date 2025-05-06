package com.example.LUPP_API.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://192.168.15.24:3000",
                        "https://lupp-app.vercel.app",
                        "https://lupp-app-xvyd.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // <-- não esqueça
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}




