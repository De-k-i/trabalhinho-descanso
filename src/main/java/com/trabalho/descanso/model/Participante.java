package com.trabalho.descanso.model;

import jakarta.persistence.*;

@Embeddable
public class Participante {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parte_id")
    private Parte parte;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_parte")
    private TipoParte tipo;

    public Participante() {
    }

    public Participante(Parte parte, TipoParte tipo) {
        if (parte == null || tipo == null) {
            throw new IllegalArgumentException("Dados inválidos.");
        }
        this.parte = parte;
        this.tipo = tipo;
    }

    public Parte getParte() {
        return parte;
    }

    public void setParte(Parte parte) {
        if (parte == null) {
            throw new IllegalArgumentException("Parte inválida.");
        }
        this.parte = parte;
    }

    public String getTipo() {
        if (tipo == null) return null;
        switch (tipo) {
            case REQUERENTE:
                return "Requerente";
            case REQUERIDO:
                return "Requerido";
            default:
                return null;
        }
    }

    public void setTipo(TipoParte tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Participante)) return false;
        Participante other = (Participante) obj;
        if (this.parte == null || other.parte == null) return false;
        return this.parte.equals(other.parte);
    }

    @Override
    public String toString() {
        return parte.getNome() + " - " + parte.getDocumento() + " - " + getTipo();
    }
}