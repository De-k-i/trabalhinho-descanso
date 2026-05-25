package com.trabalho.descanso.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trabalho.descanso.model.Pagamento;
import com.trabalho.descanso.model.Parte;
import com.trabalho.descanso.model.Participante;
import com.trabalho.descanso.model.Processo;
import com.trabalho.descanso.model.TipoParte;
import com.trabalho.descanso.model.Usuario;

@Service
public class ProcessoService {

    private final Map<String, Processo> processos = new HashMap<>();

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

    public Processo adicionarProcesso(Processo novoProcesso) {
        if (novoProcesso.getNumero() == null || novoProcesso.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Número do processo é obrigatório.");
        }
        if (novoProcesso.getJuizo() == null) {
            throw new IllegalArgumentException("O Juízo do processo é obrigatório.");
        }

        String chave = normalizarChave(novoProcesso.getNumero());

        if (processos.containsKey(chave)) {
            throw new IllegalStateException("Processo já cadastrado com este número.");
        }
        Processo processo = new Processo(novoProcesso.getNumero(), novoProcesso.getJuizo());
        processos.put(chave, processo);
        return processo;
    }

    public Processo getProcesso(String numero) {
        String chave = normalizarChave(numero);
        Processo processo = processos.get(chave);

        if (processo == null) {
            throw new IllegalArgumentException("Processo número '" + numero + "' não foi encontrado.");
        }
        return processo;
    }

    public List<Processo> listarTodos() {
        return new ArrayList<>(processos.values());
    }

    public void removerProcesso(String numero) {
        String chave = normalizarChave(numero);
        getProcesso(chave);
        processos.remove(chave);
    }

    public void vincularParte(String numeroProcesso, Parte parte, TipoParte tipo) {
        if (parte == null || parte.getDocumento() == null) {
            throw new IllegalArgumentException("Dados da parte inválidos para vinculação.");
        }
        Processo processo = getProcesso(numeroProcesso);
        processo.adicionarParte(parte, tipo);
    }

    public Pagamento adicionarPagamento(String numeroProcesso, Usuario solicitante, BigDecimal valor) {
        Processo processo = getProcesso(numeroProcesso);
        Pagamento pagamento = pagamentoService.criarPagamento(solicitante, valor);
        return processo.adicionarPagamento(pagamento);
    }

    public List<Processo> getProcessosByParte(String documento) {
        String documentoAlvo = normalizarChave(documento);
        List<Processo> processosComParte = new ArrayList<>();
        for (Processo processo : processos.values()) {
            for (Participante p : processo.getPartes()) {
                String docParte = normalizarChave(p.getParte().getDocumento());
                if (docParte.equals(documentoAlvo)) {
                    processosComParte.add(processo);
                }
            }
        }
        return processosComParte;
    }

    public void alterarParte(String numeroProcesso, String documentoParteAntiga, String nome, String documento) {
        Processo processo = getProcesso(numeroProcesso);
        Parte parteNova = parteService.getOrCreateParte(nome, documento);
        Parte parteAntiga = processo.getPartes().stream()
                .filter(p -> p.getParte().getDocumento().equals(documentoParteAntiga))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Parte antiga não encontrada neste processo."))
                .getParte();

        processo.alterarParte(parteAntiga, parteNova);
    }

    public void deletarParteComValidaçao(String documento) {
        List<Processo> processosVinculados = getProcessosByParte(documento);
        if (!processosVinculados.isEmpty()) {
            throw new IllegalStateException("Erro de integridade: Parte possui processos ativos.");
        }
        parteService.removerParte(documento);
    }
}