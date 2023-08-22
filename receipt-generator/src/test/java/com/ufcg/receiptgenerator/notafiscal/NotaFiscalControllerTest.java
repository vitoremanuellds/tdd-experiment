package com.ufcg.receiptgenerator.notafiscal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.receiptgenerator.fatura.Fatura;
import com.ufcg.receiptgenerator.fatura.InvalidFaturaValueException;
import com.ufcg.receiptgenerator.fatura.TiposDeServico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeServico.OUTROS, 1000.00);
        doReturn(new NotaFiscal(fatura.getClientName(), fatura.getValue(), fatura.getValue() * this.OUTROS_TAX_VALUE))
            .when(this.notaFiscalServiceMock)
            .generateNotaFiscal(any(Fatura.class));

        //Test
        ResponseEntity response = this.notaFiscalController.generateNotaFiscal(fatura);

        // Assert
        assertTrue(response.getBody() instanceof NotaFiscal);
    }


    @Test
    void NotaFiscalController_WhenGenerateNotaFiscalIsCalledAndInvalidFaturaValueExceptionIsThrown_ShouldReturnAStringMessageAndStatusCodeAsBadRequest() throws Exception {

        // Arrange
        doThrow(InvalidFaturaValueException.class).when(this.notaFiscalServiceMock).generateNotaFiscal(any(Fatura.class));

        //Test
        ResponseEntity response = this.notaFiscalController.generateNotaFiscal(new Fatura());

        // Assert
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }


    @Test
    void NotaFiscalController_WhenGenerateNotaFiscalIsCalledAndFaturaHasAValidValue_ShouldReturnAStatusCodeAsOk() throws Exception {

        // Arrange
        Fatura fatura = new Fatura("Alexsandro", "Rua das Bananeiras, 10", TiposDeServico.CONSULTORIA, 100.00);

        doReturn(new NotaFiscal(fatura.getClientName(), fatura.getValue(), fatura.getValue() * this.OUTROS_TAX_VALUE))
            .when(this.notaFiscalServiceMock)
            .generateNotaFiscal(any(Fatura.class));

        //Test
        ResponseEntity response = this.notaFiscalController.generateNotaFiscal(fatura);

        // Assert
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }


    
}
