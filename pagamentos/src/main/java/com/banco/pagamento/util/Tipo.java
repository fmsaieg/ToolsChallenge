package com.banco.pagamento.util;

public enum Tipo {

    AVISTA("AVISTA"),
    PARCELADOLOJA("PARCELADO LOJA"),
    PARCELADOEMISSOR("PARCELADO EMISSOR");

    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    Tipo(String tipo) {
        this.tipo = tipo;
    }
}
