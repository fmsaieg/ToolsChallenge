package com.banco.pagamento.util;

public enum Status {

    AUTORIZADO("AUTORIZADO"),
    NEGADO("NEGADO");

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Status(String status) {
        this.status = status;
    }
}
