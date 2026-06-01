package com.trabalho.descanso;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.trabalho.descanso.model.Juizo;
import com.trabalho.descanso.model.Parte;
import com.trabalho.descanso.model.Processo;
import com.trabalho.descanso.model.TipoParte;
import com.trabalho.descanso.model.Usuario;
import com.trabalho.descanso.repositories.ProcessoRepository;
import com.trabalho.descanso.services.LocalizacaoService;
import com.trabalho.descanso.services.ParteService;
import com.trabalho.descanso.services.ProcessoService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class DescansoApplication {

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ParteService parteService;

    @Autowired
    private LocalizacaoService localizacaoService;

    @Autowired
    private ProcessoRepository processoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(DescansoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void inicializarDados() {
        try {
            
            if (processoRepository.count() > 0) {
                System.out.println(">>> Banco de dados já populado. Pulando carga inicial de teste. <<<");
                return;
            }

            Juizo juizo = localizacaoService.getOrCreateJuizo(
                    "SC",
                    "Florianopolis",
                    "Justiça Estadual",
                    "1ª Vara");

            Processo proc123 = new Processo("123", juizo);
            Processo proc456 = new Processo("456", juizo);

            processoService.adicionarProcesso(proc123);
            processoService.adicionarProcesso(proc456);

            Parte p1 = parteService.getOrCreateParte("João", "111");
            Parte p2 = parteService.getOrCreateParte("Maria", "222");

            processoService.vincularParte("123", p1, TipoParte.REQUERENTE);
            processoService.vincularParte("123", p2, TipoParte.REQUERIDO);

            salvarUsuario();

            System.out.println(">>> Banco de dados MariaDB inicializado com sucesso com dados de teste! <<<");

        } catch (Exception e) {
            System.err.println("Erro ao inicializar carga de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void salvarUsuario() {
        Usuario u = new Usuario();
        u.setNome("Carlos");
        entityManager.persist(u);
        processoService.adicionarPagamento("123", u, new BigDecimal("100.50"));
    }
}