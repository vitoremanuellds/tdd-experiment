package com.ufcg.receiptgenerator.notafiscal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaFiscal {
    
    private String clientName;

    private Double value;

    private Double taxValue;

}
