package com.banco.pagamento.model;

import java.sql.Timestamp;

import com.banco.pagamento.util.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Descricao {

    private Double valor;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Timestamp dataHora;
    private String estabelecimento;
    private Long nsu;
    private Long codigoAutorizacao;
    private Status status;


}
