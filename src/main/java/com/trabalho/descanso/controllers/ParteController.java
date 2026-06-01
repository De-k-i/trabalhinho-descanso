package com.trabalho.descanso.controllers;

import com.trabalho.descanso.model.Parte;
import com.trabalho.descanso.services.ParteService;
import com.trabalho.descanso.services.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partes")
public class ParteController {

    @Autowired
    private ParteService parteService;

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Parte novaParte) {
        try {
            if (novaParte.getNome() == null || novaParte.getNome().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Nome obrigatório.");
            }
            if (novaParte.getDocumento() == null || novaParte.getDocumento().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Documento obrigatório.");
            }
            
            Parte parteSalva = parteService.cadastrarParte(novaParte);
            return ResponseEntity.status(HttpStatus.CREATED).body(parteSalva);
            
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{documento}")
    public ResponseEntity<?> selectById(@PathVariable String documento) {
        try {
            Parte parte = parteService.buscarPorDocumento(documento);
            return ResponseEntity.ok(parte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Parte>> selectAll() {
        return ResponseEntity.ok(parteService.listarTodas());
    }

    @PutMapping("/{documento}")
    public ResponseEntity<?> update(@PathVariable String documento, @RequestBody Parte dadosAtualizados) {
        try {
            if (dadosAtualizados.getNome() == null || dadosAtualizados.getNome().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: O novo nome não pode ser vazio.");
            }
            Parte updated = parteService.alterarNomeParte(documento, dadosAtualizados.getNome());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{documento}")
    public ResponseEntity<?> delete(@PathVariable String documento) {
        try {
            processoService.deletarParteComValidaçao(documento);
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}