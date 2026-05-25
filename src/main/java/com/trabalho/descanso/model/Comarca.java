/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.descanso.model;

/**
 *
 * @author deki
 */
public class Comarca {
    private String nome;
    private Estado estado;
    
    public Comarca() {
        
    }

    public Comarca(String nome, Estado estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }

}
