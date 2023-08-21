package com.ufcg.receiptgenerator.notafiscal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class NotaFiscalControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    @Mock
    private NotaFiscalService notaFiscalServiceMock;

    @InjectMocks
    private NotaFiscalController notaFiscalController;

    private final double OUTROS_TAX_VALUE = 0.06;
    private final double CONSULTORIA_TAX_VALUE = 0.25;
    private final double TREINAMENTO_TAX_VALUE = 0.15;


    @Test
    void NotaFiscalController_WhenGenerateNotaFiscalIsCalled_ShouldReturnANotaFiscal() throws Exception {

        // Arrange
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeFaturas.OUTROS, 1000.00);
        doReturn(new NotaFiscal(fatura.clientName, fatura.value, fatura.value * this.OUTROS_TAX_VALUE))
            .when(this.notaFiscalServiceMock)
            .generateNotaFiscal(any(Fatura.class));

        //Test

        ResponseEntity<NotaFiscal> response = this.notaFiscalController.generateNotaFiscal(fatura);

        // Assert

    }


    @Test
    void NotaFiscalController_WhenGenerateNotaFiscalIsCalled_GenerateANotaFiscalWithTaskValueSetAccordingToConsultoriaTaxValue() throws Exception {

        // Arrange
        Fatura fatura = new Fatura("Alexsandro", "Rua das Bananeiras, 10", TiposDeFaturas.CONSULTORIA, 100.00);

        //Test
        String notaFiscalAsString = mockMvc.perform(
            post("/api/nota-fiscal").content(
                (new ObjectMapper()).writeValueAsString(fatura)
            ).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        NotaFiscal notaFiscal = (new ObjectMapper()).readValue(notaFiscalAsString, NotaFiscal.class);

        // Assert

        assertEquals(fatura.clientName, notaFiscal.clientName);
        assertEquals(fatura.value, notaFiscal.value);
        assertEquals(fatura.value * this.CONSULTORIA_TAX_VALUE, notaFiscal.taxValue);
    }


    @Test
    void NotaFiscalController_WhenGenerateNotaFiscalIsCalled_GenerateANotaFiscalWithTaskValueSetAccordingToTreinamentoTaxValue() throws Exception {

        // Arrange
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeFaturas.TREINAMENTO, 2000.00);

        //Test
        String notaFiscalAsString = mockMvc.perform(
            post("/api/nota-fiscal").content(
                (new ObjectMapper()).writeValueAsString(fatura)
            ).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        NotaFiscal notaFiscal = (new ObjectMapper()).readValue(notaFiscalAsString, NotaFiscal.class);

        // Assert

        assertEquals(fatura.clientName, notaFiscal.clientName);
        assertEquals(fatura.value, notaFiscal.value);
        assertEquals(fatura.value * this.TREINAMENTO_TAX_VALUE, notaFiscal.taxValue);
    }


    
}
