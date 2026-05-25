/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.descanso.model;

/**
 *
 * @author deki
 */
public class Competencia {
    private String nome;
    private Comarca comarca;
    
    public Competencia() {
        
    }

    public Competencia(String nome, Comarca comarca) {
        this.nome = nome;
        this.comarca = comarca;
    }

    public String getNome() {
        return nome;
    }

    public Comarca getComarca() {
        return comarca;
    }
    
}
