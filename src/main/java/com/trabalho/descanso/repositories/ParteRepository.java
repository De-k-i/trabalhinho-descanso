package com.trabalho.descanso.repositories;

import com.trabalho.descanso.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParteRepository extends JpaRepository<Parte, Long> {
    Optional<Parte> findByDocumento(String documento);
    boolean existsByDocumento(String documento);
}