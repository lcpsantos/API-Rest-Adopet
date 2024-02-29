package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private EmailService emailService;

    @Spy //Spy é usado para criar um objeto real e ao mesmo tempo monitorar suas interações
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    private SolicitacaoAdocaoDto dto;

    @Mock
    private Abrigo abrigo;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Captor //ArgumentCaptor é usado para capturar argumentos passados para métodos
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    @DisplayName("Deve salvar uma solicitação de adoção")
    void salvarSolicitacaoAdocao() {
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        adocaoService.solicitar(dto);

        //ASSERT
        then(repository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        assertEquals(pet, adocaoSalva.getPet());
        assertEquals(tutor, adocaoSalva.getTutor());
        assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    @DisplayName("Deve validar a solicitação de adoção")
    void validarSolicitacaoAdocao() {
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        //ACT
        adocaoService.solicitar(dto);

        //ASSERT
        then(validador1).should().validar(dto);
        then(validador2).should().validar(dto);
    }

    @Test
    @DisplayName("Deve enviar email para o abrigo ao salvar uma solicitação de adoção")
    void enviarEmailAbrigo() {
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        adocaoService.solicitar(dto);

        //ASSERT
        then(emailService).should().enviarEmail(
                abrigo.getEmail(),
                "Solicitação de adoção",
                "Olá " + abrigo.getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + pet.getNome() + "." +
                        " \nFavor avaliar para aprovação ou reprovação.");
    }
}