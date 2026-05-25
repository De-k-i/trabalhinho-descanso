/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.descanso.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 *
 * @author deki
 */
public class Pagamento {

    private int id;
    private Usuario solicitante;
    private BigDecimal valor;
    private Instant dataSolicitacao;
    private Instant dataProcessamento;
    private Instant dataPagamento;
    private Instant dataCancelamento;
    private StatusPagamento status;
    
    public Pagamento() {
        
    }
    
    public Pagamento(int id, Usuario solicitante, BigDecimal valor) {
        if (solicitante == null) {
            throw new IllegalArgumentException("Solicitante não pode ser nulo!");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo!");
        }
        this.id = id;
        this.solicitante = solicitante;
        this.valor = valor;
        this.dataSolicitacao = Instant.now();
        this.status = StatusPagamento.SOLICITADO;
    }

    public void processar() {
        if (status != StatusPagamento.SOLICITADO) {
            throw new IllegalStateException("Pagamento não pode ser processado.");
        }
        this.status = StatusPagamento.PENDENTE;
        this.dataProcessamento = Instant.now();
    }

    public void pagar() {
        if (status != StatusPagamento.PENDENTE) {
            throw new IllegalStateException("Pagamento não pode ser efetivado.");
        }
        this.status = StatusPagamento.EFETIVADO;
        this.dataPagamento = Instant.now();
    }

    public void cancelar() {
        if (status == StatusPagamento.EFETIVADO) {
            throw new IllegalStateException("Pagamento não pode ser cancelado.");
        }
        this.status = StatusPagamento.CANCELADO;
        this.dataCancelamento = Instant.now();
    }

    public int getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Instant getDataSolicitacao() {
        return dataSolicitacao;
    }

    public Instant getDataProcessamento() {
        return dataProcessamento;
    }

    public Instant getDataPagamento() {
        return dataPagamento;
    }

    public Instant getDataCancelamento() {
        return dataCancelamento;
    }

}
