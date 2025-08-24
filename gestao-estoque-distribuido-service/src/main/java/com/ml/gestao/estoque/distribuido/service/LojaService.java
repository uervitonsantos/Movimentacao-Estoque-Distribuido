package com.ml.gestao.estoque.distribuido.service;

import com.google.common.base.Strings;
import com.ml.gestao.estoque.distribuido.canonico.LojaCanonico;
import com.ml.gestao.estoque.distribuido.entidade.Loja;
import com.ml.gestao.estoque.distribuido.filtro.FiltroWrapper;
import com.ml.gestao.estoque.distribuido.repository.LojaRepository;
import com.ml.gestao.estoque.distribuido.util.MensagensValidacao;
import com.ml.gestao.estoque.distribuido.util.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    public LojaCanonico buscaLoja(Long lojaID) {
        return Optional.ofNullable(lojaRepository.buscaLoja(lojaID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_LOJA_NAO_ENCONTRADA.getValor()));
    }

    public Loja buscarLoja(Long lojaID) {
        return Optional.ofNullable(lojaRepository.busca(lojaID))
                .orElseThrow(() -> new ValidacaoException(MensagensValidacao.ERRO_LOJA_NAO_ENCONTRADA.getValor()));
    }

    public List<LojaCanonico> buscaLojas(FiltroWrapper filtro) {
        return lojaRepository.buscaLojas(filtro);
    }

    public LojaCanonico criaLoja(LojaCanonico canonico) {
        validaIDLoja(canonico);
        validaDadosLoja(canonico);
        Long lojaID = salvaLoja(canonico);
        return buscaLoja(lojaID);
    }

    private void validaIDLoja(LojaCanonico canonico) {
        if (canonico.getLojaID() != null) {
            LojaCanonico lojaExistente = lojaRepository.buscaLoja(canonico.getLojaID());
            if (lojaExistente != null) {
                throw new ValidacaoException(MensagensValidacao.ERRO_LOJA_JA_EXISTE.getValor());
            }
        }
    }

    public Long editaLoja(LojaCanonico canonico) {
        Loja loja = buscarLoja(canonico.getLojaID());
        validaDadosLoja(canonico);
        populaLoja(loja, canonico);
        loja = lojaRepository.merge(loja);
        return loja.getLojaID();
    }

    private void populaLoja(Loja loja, LojaCanonico canonico) {
        loja.setNome(canonico.getNome());
        loja.setEndereco(canonico.getEndereco());
        // Movimentos podem ser tratados via EstoqueMovimentoService
    }

    private void validaDadosLoja(LojaCanonico canonico) {
        if (Strings.isNullOrEmpty(canonico.getNome())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_NOME_LOJA_OBRIGATORIO.getValor());
        }
        if (Strings.isNullOrEmpty(canonico.getEndereco())) {
            throw new ValidacaoException(MensagensValidacao.ERRO_ENDERECO_LOJA_OBRIGATORIO.getValor());
        }
    }

    private Long salvaLoja(LojaCanonico canonico) {
        Loja loja = geraLoja(canonico);
        populaLoja(loja, canonico);
        Loja salvo = lojaRepository.salvaLoja(loja);
        return salvo.getLojaID();
    }

    private Loja geraLoja(LojaCanonico canonico) {
        if (canonico.getLojaID() == null) {
            return new Loja();
        }
        return lojaRepository.busca(canonico.getLojaID());
    }

    public void removeLoja(Long lojaID) {
        Loja loja = buscarLoja(lojaID);
        lojaRepository.remove(loja);
    }
}
