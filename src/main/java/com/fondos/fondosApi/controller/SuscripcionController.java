package com.fondos.fondosApi.controller;

import com.fondos.fondosApi.dto.DesvinculacionRequest;
import com.fondos.fondosApi.dto.SuscripcionRequest;
import com.fondos.fondosApi.service.SuscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @PostMapping
    public ResponseEntity<String> subscribe(@RequestBody @Valid SuscripcionRequest request) {
        suscripcionService.subscribe(request);
        return ResponseEntity.ok("✅ Suscripción realizada con éxito");
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelarSuscripcion(@Valid @RequestBody DesvinculacionRequest request) {
        suscripcionService.desvincularDeFondo(request);
        return ResponseEntity.noContent().build();
    }

}
