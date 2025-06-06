package com.fondos.fondosApi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permite CORS para todas las rutas
        registry.addMapping("/**")
                .allowedOrigins("http://frontend-fondos-alb-41541643.us-east-1.elb.amazonaws.com") // Permite solicitudes solo desde este origen
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
