package com.trabalho.descanso.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.descanso.model.Pagamento;
import com.trabalho.descanso.model.Parte;
import com.trabalho.descanso.model.Processo;
import com.trabalho.descanso.model.TipoParte;
import com.trabalho.descanso.model.Usuario;
import com.trabalho.descanso.repositories.ProcessoRepository;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    @Lazy
    private PagamentoService pagamentoService;

    @Autowired
    @Lazy
    private ParteService parteService;

    private String normalizarChave(String input) {
        if (input == null)
            return "";
        return input.replaceAll("\\W", "").toUpperCase();
    }

    @Transactional
    public Processo adicionarProcesso(Processo novoProcesso) {
        if (novoProcesso.getNumero() == null || novoProcesso.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Número do processo é obrigatório.");
        }
        if (novoProcesso.getJuizo() == null) {
            throw new IllegalArgumentException("O Juízo do processo é obrigatório.");
        }

        String chave = normalizarChave(novoProcesso.getNumero());

        if (processoRepository.existsByNumero(chave)) {
            throw new IllegalStateException("Processo já cadastrado com este número.");
        }
        Processo processo = new Processo(novoProcesso.getNumero(), novoProcesso.getJuizo());
        return processoRepository.save(processo);
    }

    public Processo getProcesso(String numero) {
        String chave = normalizarChave(numero);
        return processoRepository.findByNumero(chave)
                .orElseThrow(() -> new IllegalArgumentException("Processo número '" + numero + "' não foi encontrado."));
    }

    public List<Processo> listarTodos() {
        return processoRepository.findAll();
    }

    @Transactional
    public void removerProcesso(String numero) {
        Processo processo = getProcesso(numero);
        processoRepository.delete(processo);
    }

    @Transactional
    public void vincularParte(String numeroProcesso, Parte parte, TipoParte tipo) {
        if (parte == null || parte.getDocumento() == null) {
            throw new IllegalArgumentException("Dados da parte inválidos para vinculação.");
        }
        Processo processo = getProcesso(numeroProcesso);
        processo.adicionarParte(parte, tipo);
        processoRepository.save(processo);
    }

    @Transactional
    public Pagamento adicionarPagamento(String numeroProcesso, Usuario solicitante, BigDecimal valor) {
        Processo processo = getProcesso(numeroProcesso);
        
        Pagamento pagamento = pagamentoService.criarPagamento(solicitante, valor);
        
        processo.adicionarPagamento(pagamento);
        processoRepository.save(processo);
        return pagamento;
    }

    public List<Processo> getProcessosByParte(String documento) {
        return processoRepository.findByParteDocumento(normalizarChave(documento));
    }

    @Transactional
    public void alterarParte(String numeroProcesso, String documentoParteAntiga, String nome, String documento) {
        Processo processo = getProcesso(numeroProcesso);
        Parte parteNova = parteService.getOrCreateParte(nome, documento);
        
        Parte parteAntiga = processo.getPartes().stream()
                .filter(p -> p.getParte().getDocumento().equals(documentoParteAntiga))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Parte antiga não encontrada neste processo."))
                .getParte();

        processo.alterarParte(parteAntiga, parteNova);
        processoRepository.save(processo);
    }

    @Transactional
    public void deletarParteComValidaçao(String documento) {
        List<Processo> processosVinculados = getProcessosByParte(documento);
        if (!processosVinculados.isEmpty()) {
            throw new IllegalStateException("Erro de integridade: Parte possui processos ativos.");
        }
        parteService.removerParte(documento);
    }
}