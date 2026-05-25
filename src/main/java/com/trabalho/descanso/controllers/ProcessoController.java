package com.trabalho.descanso.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trabalho.descanso.model.Pagamento;
import com.trabalho.descanso.model.Parte;
import com.trabalho.descanso.model.Processo;
import com.trabalho.descanso.model.TipoParte;
import com.trabalho.descanso.model.Usuario;
import com.trabalho.descanso.services.ProcessoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Processo novoProcesso) {
        try {
            Processo salvo = processoService.adicionarProcesso(novoProcesso);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Processo>> selectAll() {
        return ResponseEntity.ok(processoService.listarTodos());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<?> selectByNumero(@PathVariable String numero) {
        try {
            Processo processo = processoService.getProcesso(numero);
            return ResponseEntity.ok(processo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<?> delete(@PathVariable String numero) {
        try {
            processoService.removerProcesso(numero);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{numeroProcesso}/partes/{documentoParteAntiga}")
    public ResponseEntity<String> alterarParte(
            @PathVariable String numeroProcesso,
            @PathVariable String documentoParteAntiga,
            @RequestParam String nome,
            @RequestParam String documento) {
        if (nome == null || nome.trim().isEmpty() || documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nome e documento da nova parte são obrigatórios.");
        }

        try {
            processoService.alterarParte(numeroProcesso, documentoParteAntiga, nome, documento);
            return ResponseEntity.ok("Parte alterada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
        }
    }

    @PostMapping("/{numero}/partes")
    public ResponseEntity<?> vincularParte(@PathVariable String numero,
            @RequestBody Parte parte,
            @RequestParam TipoParte tipo) {
        try {
            processoService.vincularParte(numero, parte, tipo);
            return ResponseEntity.ok("Parte vinculada com sucesso ao processo.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{numero}/pagamentos")
    public ResponseEntity<?> adicionarPagamento(@PathVariable String numero,
            @RequestBody Map<String, Object> payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Usuario solicitante = mapper.convertValue(payload.get("solicitante"), Usuario.class);
            BigDecimal valor = new BigDecimal(payload.get("valor").toString());

            Pagamento pagamento = processoService.adicionarPagamento(numero, solicitante, valor);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar pagamento: " + e.getMessage());
        }
    }
}