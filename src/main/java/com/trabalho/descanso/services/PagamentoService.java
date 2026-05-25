package com.trabalho.descanso.services;

import com.trabalho.descanso.model.Pagamento;
import com.trabalho.descanso.model.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final Map<Integer, Pagamento> pagamentos = new HashMap<>();
    private int idProximoPagamento = 1;

    public Pagamento criarPagamento(Usuario solicitante, BigDecimal valor) {
        if (solicitante == null || solicitante.getNome() == null || solicitante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O usuário solicitante do pagamento é obrigatório.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }

        int id = idProximoPagamento++;
        Pagamento novoPagamento = new Pagamento(id, solicitante, valor);
        
        pagamentos.put(id, novoPagamento);
        
        return novoPagamento;
    }

    public Pagamento buscarPorId(Integer id) {
        Pagamento pagamento = pagamentos.get(id);
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento com ID " + id + " não encontrado.");
        }
        return pagamento;
    }

    public List<Pagamento> listarTodos() {
        return new ArrayList<>(pagamentos.values());
    }
}