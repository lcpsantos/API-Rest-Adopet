package br.com.alura.adopet.api.validacoes.impl;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.alura.adopet.api.constants.MessagesConstants.ERRO_TUTOR_ADOCAO_AVALIACAO;

@Component
public class ValidacaoTutorComAdocaoEmAndamentoImpl implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        boolean tutorTemAdocaoEmAndamento = adocaoRepository
                .existsByTutorIdAndStatus(dto.idPet(),
                        StatusAdocao.AGUARDANDO_AVALIACAO);

        if (tutorTemAdocaoEmAndamento) {
            throw new ValidacaoException(ERRO_TUTOR_ADOCAO_AVALIACAO);
        }
    }
}
