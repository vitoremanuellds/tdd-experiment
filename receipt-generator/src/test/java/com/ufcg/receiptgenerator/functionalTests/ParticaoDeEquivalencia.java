package com.ufcg.receiptgenerator.functionalTests;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.receiptgenerator.fatura.Fatura;
import com.ufcg.receiptgenerator.fatura.TiposDeServico;
import com.ufcg.receiptgenerator.notafiscal.NotaFiscal;

@SpringBootTest
@AutoConfigureMockMvc
public class ParticaoDeEquivalencia {
    
    @Autowired
    private MockMvc mockMvc;


    @Test
    void notaFiscalController_WhenInvalidNomeIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("", "Rua dos Bobos, 0", TiposDeServico.OUTROS, 50.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenInvalidEnderecoIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Vitor", null, TiposDeServico.TREINAMENTO, 25.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenInvalidTipoDeServicoIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        String fatura = "{\"clientName\":\"\",\"endereco\":\"Rua dos Bobos, 0\",\"tipoDoServico\":\"VENDAS\",\"value\":50.0}";

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenInvalidValueIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeServico.CONSULTORIA, -30.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenAValidFaturaIsPassedInsideTheFatura_ShouldReturnANotaFiscal() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Dáphine", "Rua da Alvorada", TiposDeServico.OUTROS, 190.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
