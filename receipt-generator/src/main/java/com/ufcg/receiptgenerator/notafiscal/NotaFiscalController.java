package com.ufcg.receiptgenerator.notafiscal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.receiptgenerator.fatura.Fatura;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nota-fiscal")
@RequiredArgsConstructor
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService notaFiscalService;


    @PostMapping
    public ResponseEntity<?> generateNotaFiscal(@RequestBody Fatura fatura) {
        try {
            NotaFiscal notaFiscal = this.notaFiscalService.generateNotaFiscal(fatura);
            return new ResponseEntity<NotaFiscal>(notaFiscal, HttpStatus.OK);
        } catch ( Exception e ) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}
