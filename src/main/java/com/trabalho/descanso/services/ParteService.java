package com.trabalho.descanso.services;

import com.trabalho.descanso.model.Parte;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParteService {

    private final Map<String, Parte> partes = new HashMap<>();

    public String normalizar(String input) {
        if (input == null) return "";
        return input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    public Parte cadastrarParte(Parte novaParte) {
        String chave = normalizar(novaParte.getDocumento());
        if (partes.containsKey(chave)) {
            throw new IllegalStateException("Já existe uma parte cadastrada com este documento.");
        }
        Parte salva = new Parte(novaParte.getNome(), novaParte.getDocumento());
        partes.put(chave, salva);
        return salva;
    }

    public Parte buscarPorDocumento(String documento) {
        String chave = normalizar(documento);
        Parte parte = partes.get(chave);
        if (parte == null) {
            throw new IllegalArgumentException("Parte não encontrada.");
        }
        return parte;
    }

    public List<Parte> listarTodas() {
        return new ArrayList<>(partes.values());
    }

    public Parte alterarNomeParte(String documento, String novoNome) {
        Parte parte = buscarPorDocumento(documento);
        parte.setNome(novoNome);
        return parte;
    }

    public void removerParte(String documento) {
        String chave = normalizar(documento);
        buscarPorDocumento(documento);     
        partes.remove(chave);
    }

    public Parte getOrCreateParte(String nome, String documento) {
        String chave = normalizar(documento);
        return partes.computeIfAbsent(chave, k -> new Parte(nome, documento));
    }
}