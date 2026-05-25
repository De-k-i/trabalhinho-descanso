/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.descanso.model;

/**
 *
 * @author deki
 */
public class Participante {

    private Parte parte;
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
        switch (tipo) {
            case REQUERENTE:
                return "Requerente";
            case REQUERIDO:
                return "Requerido";
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Participante)) {
            return false;
        }
        return this.parte.equals(((Participante) obj).parte);
    }

    @Override
    public String toString() {
        return parte.getNome() + " - " + parte.getDocumento() + " - " + getTipo();
    }

}
