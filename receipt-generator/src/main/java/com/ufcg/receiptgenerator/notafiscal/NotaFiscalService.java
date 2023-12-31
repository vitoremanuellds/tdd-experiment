package com.ufcg.receiptgenerator.notafiscal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.receiptgenerator.communication.SAP;
import com.ufcg.receiptgenerator.communication.SMTP;
import com.ufcg.receiptgenerator.fatura.Fatura;
import com.ufcg.receiptgenerator.fatura.InvalidFaturaValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaFiscalService {

    @Autowired
    private NotaFiscalDAO notaFiscalDAO;

    @Autowired
    private SAP sap;

    @Autowired
    private SMTP smtp;

    public NotaFiscal generateNotaFiscal(Fatura fatura) throws InvalidFaturaValueException {
        
        if (fatura.getValue() < 0) throw new InvalidFaturaValueException("O valor da fatura é inválido! Valor não pode ser negativo!");
        if (fatura.getClientName() == null || fatura.getClientName().isEmpty()) throw new InvalidFaturaValueException("O nome do cliente na fatura é inválido! O nome não pode ser vazio!");
        if (fatura.getEndereco() == null || fatura.getEndereco().isEmpty()) throw new InvalidFaturaValueException("O endereço do cliente na fatura é inválido! O endereço não pode ser vazio!");
        
        NotaFiscal notaFiscal = new NotaFiscal();
        
        notaFiscal.setClientName(fatura.getClientName());
        notaFiscal.setValue(fatura.getValue());
        notaFiscal.setTaxValue(fatura.getTipoDoServico().getValue() * fatura.getValue());

        this.notaFiscalDAO.salva(notaFiscal);
        this.sap.envia(notaFiscal);
        this.smtp.envia(notaFiscal);

        return notaFiscal;
    }
}
