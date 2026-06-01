package com.trabalho.descanso.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Deixamos o banco gerar o ID automaticamente

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario solicitante;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    private Instant dataSolicitacao;
    private Instant dataProcessamento;
    private Instant dataPagamento;
    private Instant dataCancelamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    
    public Pagamento() {
    }
    
    public Pagamento(Usuario solicitante, BigDecimal valor) {
        if (solicitante == null) {
            throw new IllegalArgumentException("Solicitante não pode ser nulo!");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo!");
        }
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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