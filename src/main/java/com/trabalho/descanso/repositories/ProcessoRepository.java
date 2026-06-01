package com.trabalho.descanso.repositories;

import com.trabalho.descanso.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Optional<Processo> findByNumero(String numero);
    boolean existsByNumero(String numero);

    @Query("SELECT p FROM Processo p JOIN p.partes part WHERE part.parte.documento = :documento")
    List<Processo> findByParteDocumento(@Param("documento") String documento);
}