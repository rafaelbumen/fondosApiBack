package com.fondos.fondosApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.fondos.fondosApi.exception.ValidationExceptionHandler;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ComponentScan(basePackages = "com.fondos.fondosApi")
@SpringBootApplication
public class FondosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FondosApiApplication.class, args);
	}

	@Autowired
	private DynamoDbClient dynamoDbClient;

	@PostConstruct
	public void testConnection() {
		System.out.println("✅ DynamoDB está listo: " + dynamoDbClient);
	}

	@Bean
	public ValidationExceptionHandler validationExceptionHandler() {
		return new ValidationExceptionHandler();
	}

}
