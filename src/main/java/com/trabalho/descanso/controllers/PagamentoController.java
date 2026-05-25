package com.trabalho.descanso.controllers;

import com.trabalho.descanso.model.Pagamento;
import com.trabalho.descanso.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    // GET /api/pagamentos -> Lista todas as guias/pagamentos do sistema
    @GetMapping
    public ResponseEntity<List<Pagamento>> selectAll() {
        return ResponseEntity.ok(pagamentoService.listarTodos()); // HTTP 200
    }

    // GET /api/pagamentos/{id} -> Busca os detalhes de um pagamento específico
    @GetMapping("/{id}")
    public ResponseEntity<?> selectById(@PathVariable Integer id) {
        try {
            Pagamento pagamento = pagamentoService.buscarPorId(id);
            return ResponseEntity.ok(pagamento); // HTTP 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage()); // HTTP 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}