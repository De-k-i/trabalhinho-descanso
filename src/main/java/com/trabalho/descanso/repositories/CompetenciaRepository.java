package com.trabalho.descanso.repositories;

import com.trabalho.descanso.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    Optional<Competencia> findByNomeAndComarcaId(String nome, Long comarcaId);
}