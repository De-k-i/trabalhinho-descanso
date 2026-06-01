package com.trabalho.descanso.repositories;

import com.trabalho.descanso.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JuizoRepository extends JpaRepository<Juizo, Long> {
    Optional<Juizo> findByNomeAndCompetenciaId(String nome, Long competenciaId);
}