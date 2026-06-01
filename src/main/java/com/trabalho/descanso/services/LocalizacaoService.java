package com.trabalho.descanso.services;

import com.trabalho.descanso.model.*;
import com.trabalho.descanso.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class LocalizacaoService {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private ComarcaRepository comarcaRepository;
    @Autowired
    private CompetenciaRepository competenciaRepository;
    @Autowired
    private JuizoRepository juizoRepository;

    public String normalizar(String input) {
        if (input == null) return "";
        return input.trim().toUpperCase().replaceAll("[^A-Z0-9 ]", "");
    }

    @Transactional
    public Juizo getOrCreateJuizo(String nomeEstado, String nomeComarca, String nomeCompetencia, String nomeJuizo) {
        if (nomeEstado == null || nomeComarca == null || nomeCompetencia == null || nomeJuizo == null) {
            throw new IllegalArgumentException("Todos os campos da localização hierárquica são obrigatórios.");
        }

        Estado estado = estadoRepository.findByNome(nomeEstado.trim())
                .orElseGet(() -> estadoRepository.save(new Estado(nomeEstado.trim())));

        Comarca comarca = comarcaRepository.findByNomeAndEstadoId(nomeComarca.trim(), estado.getId())
                .orElseGet(() -> comarcaRepository.save(new Comarca(nomeComarca.trim(), estado)));

        Competencia competencia = competenciaRepository.findByNomeAndComarcaId(nomeCompetencia.trim(), comarca.getId())
                .orElseGet(() -> competenciaRepository.save(new Competencia(nomeCompetencia.trim(), comarca)));

        return juizoRepository.findByNomeAndCompetenciaId(nomeJuizo.trim(), competencia.getId())
                .orElseGet(() -> juizoRepository.save(new Juizo(nomeJuizo.trim(), competencia)));
    }

    public List<Juizo> listarJuizos() {
        return juizoRepository.findAll();
    }

    public List<Comarca> listarComarcas() {
        return comarcaRepository.findAll();
    }
}