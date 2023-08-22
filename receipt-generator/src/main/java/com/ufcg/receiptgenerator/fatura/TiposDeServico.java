package com.ufcg.receiptgenerator.fatura;

public enum TiposDeServico {

    CONSULTORIA(0.25), TREINAMENTO(0.15), OUTROS(0.16);

    private Double value;

    TiposDeServico(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return this.value;
    }
}
