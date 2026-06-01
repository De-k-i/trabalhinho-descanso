package com.trabalho.descanso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "partes")
public class Parte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true) 
    private String documento;
    
    public Parte() {}

    public Parte(String nome, String documento) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento inválido.");
        }
        this.nome = nome.trim();
        this.documento = documento.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Parte)) return false;
        Parte other = (Parte) obj;
        if (this.documento == null || other.getDocumento() == null) return false;
        return this.documento.equals(other.getDocumento());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(this.documento);
    }
}