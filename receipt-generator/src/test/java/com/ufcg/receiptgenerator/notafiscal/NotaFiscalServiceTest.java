package com.ufcg.receiptgenerator.notafiscal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.receiptgenerator.communication.SAP;
import com.ufcg.receiptgenerator.communication.SMTP;
import com.ufcg.receiptgenerator.fatura.Fatura;
import com.ufcg.receiptgenerator.fatura.TiposDeServico;

@SpringBootTest
public class NotaFiscalServiceTest {

    @Mock
    private NotaFiscalDAO notaFiscalDAOMock;

    @Mock
    private SAP sapMock;

    @Mock
    private SMTP smtpMock;

    @InjectMocks
    private NotaFiscalService notaFiscalService;

    private final double OUTROS_TAX_VALUE = 0.06;
    private final double CONSULTORIA_TAX_VALUE = 0.25;
    private final double TREINAMENTO_TAX_VALUE = 0.15;

    // @BeforeAll
    // void setUp() {
    //     this.notaFiscalDAOMock = mock(NotaFiscalDAOMock.class);
    //     this.sapMock = mock(SAP.class);
    //     this.smtpMock = mock(SMTP.class);

    //     this.notaFiscalService = new NotaFiscalService(this.notaFiscalDAOMock, this.sapMock, this.smtpMock);
    // }
    
    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldGenerateANotaFiscalWithSameValueAsFatura() {
        // Arrange
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeServico.OUTROS, 1000.00);

        // Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        assertEquals(fatura.getValue(), notaFiscal.getValue());
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldGenerateANotaFiscalWithSameClientNameAsFatura() {
        // Arrange
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeServico.OUTROS, 1000.00);

        // Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        assertEquals(fatura.getClientName(), notaFiscal.getClientName());
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldGenerateANotaFiscalWithTaxValueAccordingToConsultoriaTaxValueAndFaturasValue() {
        // Arrange
        Fatura fatura = new Fatura("Alexsandro", "Rua das Bananeiras, 10", TiposDeServico.CONSULTORIA, 100.00);

        //Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        assertEquals(fatura.getValue() * this.CONSULTORIA_TAX_VALUE, notaFiscal.getTaxValue());
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldGenerateANotaFiscalWithTaxValueAccordingToTreinamentoTaxValueAndFaturasValue() {
        // Arrange
        Fatura fatura = new Fatura("Bernardo", "Rua dos Abacates, 100", TiposDeServico.TREINAMENTO, 2000.00);

        //Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        assertEquals(fatura.getValue() * this.TREINAMENTO_TAX_VALUE, notaFiscal.getTaxValue());
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldGenerateANotaFiscalWithTaxValueAccordingToOutrosTaxValueAndFaturasValue() {
        // Arrange
        Fatura fatura = new Fatura("Vitor", "Rua dos Bobos, 0", TiposDeServico.OUTROS, 1000.00);

        // Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        assertEquals(fatura.getValue() * this.OUTROS_TAX_VALUE, notaFiscal.getTaxValue());
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldCallSalvaMethodFromNotaFiscalDAOClass() {
        Fatura fatura = new Fatura("Bernardo", "Rua dos Abacates, 100", TiposDeServico.TREINAMENTO, 2000.00);

        //Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        // assertEquals(fatura.value * this.TREINAMENTO_TAX_VALUE, notaFiscal.taxValue);
        verify(this.notaFiscalDAOMock).salva(notaFiscal);
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldCallEnviaMethodFromSAPClass() {
        Fatura fatura = new Fatura("Bernardo", "Rua dos Abacates, 100", TiposDeServico.TREINAMENTO, 2000.00);

        //Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        // assertEquals(fatura.value * this.TREINAMENTO_TAX_VALUE, notaFiscal.taxValue);
        verify(this.sapMock).envia(notaFiscal);
    }

    @Test
    void notaFiscalService_WhenGenerateNotaFiscalIsCalled_ShouldCallEnviaMethodFromSMTPClass() {
        Fatura fatura = new Fatura("Bernardo", "Rua dos Abacates, 100", TiposDeServico.TREINAMENTO, 2000.00);

        //Test
        NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);

        // Assert
        // assertEquals(fatura.value * this.TREINAMENTO_TAX_VALUE, notaFiscal.taxValue);
        verify(this.smtpMock).envia(notaFiscal);
    }
}
