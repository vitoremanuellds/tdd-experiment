package com.ufcg.receiptgenerator.notafiscal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class NotaFiscalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final double OUTROS_TAX_VALUE = 0.06;
    private final double CONSULTORIA_TAX_VALUE = 0.06;
    private final double TREINAMENTO_TAX_VALUE = 0.06;


    @Test
    void NotaFiscalController_WhenGenerateNotaFiscalIsCalled_GenerateANotaFiscalWithTheSameValueAsFatura() throws Exception {

        // Arrange
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeFaturas.OUTROS, 1000.00);

        //Test
        String notaFiscalAsString = mockMvc.perform(
            post("/api/nota-fiscal").content(
                (new ObjectMapper()).writeValueAsString(fatura)
            ).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        NotaFiscal notaFiscal = (new ObjectMapper()).readValue(notaFiscalAsString, NotaFiscal.getClass());

        // Assert

        assertEquals(fatura.clientName, notaFiscal.clientName);
        assertEquals(fatura.value, notaFiscal.value);
        assertEquals(fatura.value * this.OUTROS_TAX_VALUE, notaFiscal.taxValue);
    }
    
}
