package com.trabalho.descanso.services;

import com.trabalho.descanso.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LocalizacaoService {

    private final Map<String, Estado> estados = new HashMap<>();
    private final Map<String, Comarca> comarcas = new HashMap<>();
    private final Map<String, Competencia> competencias = new HashMap<>();
    private final Map<String, Juizo> juizos = new HashMap<>();

    public String normalizar(String input) {
        if (input == null) return "";
        return input.trim().toUpperCase().replaceAll("[^A-Z0-9 ]", "");
    }

    public Juizo getOrCreateJuizo(String nomeEstado, String nomeComarca, String nomeCompetencia, String nomeJuizo) {
        if (nomeEstado == null || nomeComarca == null || nomeCompetencia == null || nomeJuizo == null) {
            throw new IllegalArgumentException("Todos os campos da localização hierárquica são obrigatórios.");
        }

        String chaveEstado = normalizar(nomeEstado);
        String chaveComarca = chaveEstado + ":" + normalizar(nomeComarca);
        String chaveCompetencia = chaveComarca + ":" + normalizar(nomeCompetencia);
        String chaveJuizo = chaveCompetencia + ":" + normalizar(nomeJuizo);

        Estado estado = estados.computeIfAbsent(
                chaveEstado,
                k -> new Estado(nomeEstado));

        Comarca comarca = comarcas.computeIfAbsent(
                chaveComarca,
                k -> new Comarca(nomeComarca, estado));

        Competencia competencia = competencias.computeIfAbsent(
                chaveCompetencia,
                k -> new Competencia(nomeCompetencia, comarca));

        return juizos.computeIfAbsent(
                chaveJuizo,
                k -> new Juizo(nomeJuizo, competencia));
    }

    public List<Juizo> listarJuizos() {
        return new ArrayList<>(juizos.values());
    }

    public List<Comarca> listarComarcas() {
        return new ArrayList<>(comarcas.values());
    }
}