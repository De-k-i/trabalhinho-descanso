/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalho.descanso.model;

/**
 *
 * @author deki
 */
public class Parte {

    private String nome;
    private String documento;
    
    public Parte() {}

    public Parte(String nome, String documento) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento inválido.");
        }
        String nomeTrimmed = nome.trim();
        String documentoTrimmed = documento.trim();
        this.nome = nomeTrimmed;
        this.documento = documentoTrimmed;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Parte)) {
            return false;
        }

        return this.documento.equals(((Parte) obj).getDocumento());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(this.documento);
    }

}
