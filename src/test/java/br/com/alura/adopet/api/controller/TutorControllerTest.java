package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc //indica que o MockMvc deve ser configurado automaticamente
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorService service;

    @Test
    void deveRetornarStatus200QuandoBuscarTutores() throws Exception {
        String json = """
                {
                    "nome": "João",
                    "telefone": "(21)0000-0000",
                    "email": "email@example.com.br"
                }
                """;

        //Act
        var response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarStatus400QuandoNomeForNulo() throws Exception {
        String json = """
                {
                    "telefone": "(21)0000-0000",
                    "email": ""
                }
                """;

        //Act
        var response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(400, response.getStatus());
    }
}