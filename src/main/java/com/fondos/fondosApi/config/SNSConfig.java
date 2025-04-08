package com.fondos.fondosApi.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Asegúrate de que esta clase esté anotada con @Configuration
public class SNSConfig {

    @Bean  // Definir el bean para AmazonSNS
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard().build();  // Cliente SNS predeterminado
    }
}
