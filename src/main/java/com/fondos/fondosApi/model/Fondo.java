package com.fondos.fondosApi.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Fondo {

    private String id;
    private String nombre;
    private Long montoMinimo;
    private String categoria;
    private Long totalAportes;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(Long montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getTotalAportes() {
        return totalAportes;
    }

    public void setTotalAportes(Long totalAportes) {
        this.totalAportes = totalAportes;
    }
}
