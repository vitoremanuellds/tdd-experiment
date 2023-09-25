package com.ufcg.receiptgenerator.junit5Tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class TabelaDeDecisao {
    
    @Autowired
    private MockMvc mockMvc;


    @Test
    void notaFiscalController_WhenTipoDeServicoIsOutros_ShouldReturnANotaFiscalWithTaxValueAsSixPercent() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeServico.OUTROS, 50.0);

        NotaFiscal notaFiscal = new NotaFiscal("Vitor", 50.0, 3.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string((new ObjectMapper()).writeValueAsString(notaFiscal)));
    }

    @Test
    void notaFiscalController_WhenTipoDeServicoIsTreinamento_ShouldReturnANotaFiscalWithTaxValueAsFifteenPercent() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Daphne", "Rua dos Bobos, 0", TiposDeServico.TREINAMENTO, 25.0);

        NotaFiscal notaFiscal = new NotaFiscal("Daphne", 25.0, 3.75);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string((new ObjectMapper()).writeValueAsString(notaFiscal)));
    }

    @Test
    void notaFiscalController_WhenTipoDeServicoIsConsultoria_ShouldReturnANotaFiscalWithTaxValueAsTwentyFivePercent() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Eduarda", "Rua dos Bobos, 0", TiposDeServico.CONSULTORIA, 27.0);

        NotaFiscal notaFiscal = new NotaFiscal("Eduarda", 27.0, 6.75);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string((new ObjectMapper()).writeValueAsString(notaFiscal)));
    }

    @Test
    void notaFiscalController_WhenInvalidNomeIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("", "Rua da Alvorada, 0", TiposDeServico.CONSULTORIA, 190.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenInvalidEnderecoIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Vitor", "", TiposDeServico.CONSULTORIA, 70.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenInvalidTipoDeServicoIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        String fatura = "{\"clientName\":\"Hebert\",\"endereco\":\"Rua dos Bobos, 0\",\"tipoDoServico\":\"VENDAS\",\"value\":30.0}";

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void notaFiscalController_WhenInvalidValueIsPassedInsideTheFatura_ShouldReturnABadRequestCode() throws JsonProcessingException, Exception {

        Fatura fatura = new Fatura("Eduarda", "Rua da Alvorada", TiposDeServico.CONSULTORIA, -58.0);

        this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/nota-fiscal")
            .content((new ObjectMapper()).writeValueAsString(fatura))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
}
