package com.trabalho.descanso.controllers;

import com.trabalho.descanso.model.Juizo;
import com.trabalho.descanso.model.Comarca;
import com.trabalho.descanso.services.LocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/localizacoes")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @PostMapping("/juizos")
    public ResponseEntity<?> obterOuCriarJuizo(
            @RequestParam String estado,
            @RequestParam String comarca,
            @RequestParam String competencia,
            @RequestParam String juizo) {
        try {
            Juizo juizoInstancia = localizacaoService.getOrCreateJuizo(estado, comarca, competencia, juizo);
            return ResponseEntity.status(HttpStatus.CREATED).body(juizoInstancia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/juizos")
    public ResponseEntity<List<Juizo>> listarTodosJuizos() {
        return ResponseEntity.ok(localizacaoService.listarJuizos());
    }

    @GetMapping("/comarcas")
    public ResponseEntity<List<Comarca>> listarTodasComarcas() {
        return ResponseEntity.ok(localizacaoService.listarComarcas());
    }
}