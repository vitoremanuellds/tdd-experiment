package com.ufcg.receiptgenerator.fatura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fatura {
    
    private String clientName;

    private String endereco;

    private TiposDeServico tipoDoServico;

    private Double value;

}
