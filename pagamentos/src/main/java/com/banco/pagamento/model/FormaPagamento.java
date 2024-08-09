package com.banco.pagamento.model;

import com.banco.pagamento.util.Tipo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {

    private Tipo tipo;
    private int parcelas;

}
