package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest //indica que a classe de teste precisa de um contexto do Spring para ser executada
@AutoConfigureMockMvc //indica que o MockMvc deve ser configurado automaticamente
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean //indica que o Mock do AdocaoService deve ser injetado no contexto do Spring
    private AdocaoService service;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jsonDto;

    @Test
    void deveriaDevolverCodigo400QuandoSolicitarAdocaoComDadosInvalidos() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(
            post("/adocoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)//indica que o conteúdo da requisição é um JSON
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
void deveriaDevolverCodigo200QuandoSolicitarAdocaoComDadosValidos() throws Exception {
        //ARRANGE

//        String json = """
//                {
//                    "idPet": 1,
//                    "idInteressado": 1,
//                    "motivo": "Motivo qualquer"
//                }
//                """;
        var dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo qualquer");

        //ACT
        var response = mvc.perform(
            post("/adocoes")
                .content(jsonDto.write(dto).getJson())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }
}