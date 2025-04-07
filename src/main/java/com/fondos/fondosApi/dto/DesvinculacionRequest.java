package com.fondos.fondosApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DesvinculacionRequest {
    @NotBlank(message = "El ID del usuario es obligatorio")
    private String userId;

    @NotBlank(message = "El ID del fondo es obligatorio")
    private String fondoId;
}
