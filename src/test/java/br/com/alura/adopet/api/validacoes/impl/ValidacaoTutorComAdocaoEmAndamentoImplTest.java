package br.com.alura.adopet.api.validacoes.impl;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoImplTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamentoImpl validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deve validar que o tutor não tem adoção em andamento")
    void testValidar() {
        //Arrange
        when(adocaoRepository.existsByTutorIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(false);

        // Assert + Act
        assertDoesNotThrow(() -> validacao.validar(dto)); //verifica se não lança exceção
    }

    @Test
    @DisplayName("Não deve permitir solicitação de adoção para tutor com adoção em andamento")
    void testValidacaoException() {
        //Arrange
        given(adocaoRepository.existsByTutorIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        // Assert + Act
        assertThrows(ValidacaoException.class, () -> validacao.validar(dto)); //verifica se lança exceção
    }
}