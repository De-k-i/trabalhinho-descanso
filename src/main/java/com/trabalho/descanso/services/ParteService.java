package com.trabalho.descanso.services;

import com.trabalho.descanso.model.Parte;
import com.trabalho.descanso.repositories.ParteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ParteService {

    @Autowired
    private ParteRepository parteRepository;

    public String normalizar(String input) {
        if (input == null) return "";
        return input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    @Transactional
    public Parte cadastrarParte(Parte novaParte) {
        String chave = normalizar(novaParte.getDocumento());
        if (parteRepository.existsByDocumento(chave)) {
            throw new IllegalStateException("Já existe uma parte cadastrada com este documento.");
        }
        Parte salva = new Parte(novaParte.getNome(), chave);
        return parteRepository.save(salva);
    }

    public Parte buscarPorDocumento(String documento) {
        String chave = normalizar(documento);
        return parteRepository.findByDocumento(chave)
                .orElseThrow(() -> new IllegalArgumentException("Parte não encontrada."));
    }

    public List<Parte> listarTodas() {
        return parteRepository.findAll();
    }

    @Transactional
    public Parte alterarNomeParte(String documento, String novoNome) {
        Parte parte = buscarPorDocumento(documento);
        parte.setNome(novoNome);
        return parteRepository.save(parte);
    }

    @Transactional
    public void removerParte(String documento) {
        Parte parte = buscarPorDocumento(documento);     
        parteRepository.delete(parte);
    }

    @Transactional
    public Parte getOrCreateParte(String nome, String documento) {
        String chave = normalizar(documento);
        return parteRepository.findByDocumento(chave)
                .orElseGet(() -> parteRepository.save(new Parte(nome, chave)));
    }
}