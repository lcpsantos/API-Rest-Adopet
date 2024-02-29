package br.com.alura.adopet.api.validacoes.impl;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelImplTest {

    @Mock
    private PetRepository repository;

    @Mock
    private Pet pet;

    @InjectMocks
    private ValidacaoPetDisponivelImpl validacao;

    @Test
    @DisplayName("Deve validar que o pet está disponível para adoção")
    void testValidar() {
       var input = criarSolicitacaoAdocaoDto();

        when(repository.getReferenceById(input.idPet())).thenReturn(pet);
        when(pet.getAdotado()).thenReturn(false);

        // Assert + Act
        assertDoesNotThrow(() -> validacao.validar(input)); //verifica se não lança exceção
    }

    @Test
    @DisplayName("Não deve permitir solicitação de adoção para pet já adotado")
    void testValidacaoException() {
        var input = criarSolicitacaoAdocaoDto();

        when(repository.getReferenceById(input.idPet())).thenReturn(pet);
        when(pet.getAdotado()).thenReturn(true);

        assertThrows(ValidacaoException.class, () -> validacao.validar(input)); //verifica se lança exceção
    }

    private SolicitacaoAdocaoDto criarSolicitacaoAdocaoDto() {
        return new SolicitacaoAdocaoDto(
                1L,
                2L,
                "Motivo qualquer"

        );
    }
}
