package com.ufcg.receiptgenerator.communication;

import org.springframework.stereotype.Service;

import com.ufcg.receiptgenerator.notafiscal.NotaFiscal;


@Service
public class SAP {
    
    public void envia(NotaFiscal nf) { 
		System.out.println("enviando pro sap"); 
	}

}
