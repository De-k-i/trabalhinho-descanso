package com.trabalho.descanso.services;

import com.trabalho.descanso.model.Pagamento;
import com.trabalho.descanso.model.Usuario;
import com.trabalho.descanso.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Transactional
    public Pagamento criarPagamento(Usuario solicitante, BigDecimal valor) {
        if (solicitante == null || solicitante.getNome() == null || solicitante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O usuário solicitante do pagamento é obrigatório.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }
        Pagamento novoPagamento = new Pagamento(solicitante, valor);
        return pagamentoRepository.save(novoPagamento);
    }

    public Pagamento buscarPorId(Integer id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento com ID " + id + " não encontrado."));
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }
}