package com.trabalho.descanso.model;

public class Juizo {
    private String nome;
    private Competencia competencia;
    
    public Juizo() {
        
    }

    public Juizo(String nome, Competencia competencia) {
        this.nome = nome;
        this.competencia = competencia;
    }

    public String getNome() {
        return nome;
    }

    public Competencia getCompetencia() {
        return competencia;
    }
    
}
