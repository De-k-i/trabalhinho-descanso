package com.trabalho.descanso.repositories;

import com.trabalho.descanso.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComarcaRepository extends JpaRepository<Comarca, Long> {
    Optional<Comarca> findByNomeAndEstadoId(String nome, Long estadoId);
}