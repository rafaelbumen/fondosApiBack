package com.fondos.fondosApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class User {

    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private Long balance;
    private String notificationMethod; // "email" o "sms"
    private List<Transaction> transactions = new ArrayList<>();
    private List<Suscripcion> suscripciones = new ArrayList<>();
    private String password;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
