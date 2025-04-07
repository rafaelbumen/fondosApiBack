package com.fondos.fondosApi.controller;

import com.fondos.fondosApi.model.Fondo;
import com.fondos.fondosApi.service.FondoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fondos")
public class FondoController {

    private final FondoService fondoService;

    public FondoController(FondoService fondoService) {
        this.fondoService = fondoService;
    }

    @GetMapping
    public List<Fondo> getAllFondos() {
        return fondoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fondo> getFondoById(@PathVariable String id) {
        return fondoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

