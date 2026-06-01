package com.trabalho.descanso.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "processos")
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "processo_participantes", joinColumns = @JoinColumn(name = "processo_id"))
    private List<Participante> partes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "juizo_id", nullable = false)
    private Juizo juizo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "processo_id")
    @MapKeyColumn(name = "pagamento_map_key")
    private Map<Integer, Pagamento> pagamentos;

    public Processo() {
    }

    public Processo(String numero, Juizo juizo) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("Número inválido.");
        }
        if (juizo == null) {
            throw new IllegalArgumentException("Juízo não pode ser nulo.");
        }

        this.numero = numero.trim();
        this.juizo = juizo;
        this.partes = new ArrayList<>();
        this.pagamentos = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Juizo getJuizo() {
        return juizo;
    }

    public void setJuizo(Juizo juizo) {
        this.juizo = juizo;
    }

    public void adicionarParte(Parte parte, TipoParte tipo) {
        if (parte == null || tipo == null) {
            throw new IllegalArgumentException("Parte e tipo são obrigatórios.");
        }
        this.partes.add(new Participante(parte, tipo));
    }

    public void alterarParte(Parte parteAntiga, Parte parteNova) {
        if (parteAntiga == null || parteNova == null) {
            throw new IllegalArgumentException("Parte e tipo são obrigatórios.");
        }
        for (Participante p : partes) {
            if (p.getParte().equals(parteAntiga)) {
                p.setParte(parteNova);
            }
        }
    }

    public List<Participante> getPartes() {
        return Collections.unmodifiableList(partes);
    }

    public Pagamento adicionarPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não pode ser nulo.");
        }
        pagamentos.put(pagamento.getId(), pagamento);
        return pagamento;
    }

    public Pagamento getPagamento(int id) {
        return pagamentos.get(id);
    }

    public Collection<Pagamento> getPagamentos() {
        return new ArrayList<>(pagamentos.values());
    }

    @Override
    public String toString() {
        return "Processo " + numero;
    }
}