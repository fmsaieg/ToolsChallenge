package com.banco.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.pagamento.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {
    // Verifica se existe uma transação com um determinado NSU
    boolean existsByDescricaoNsu(Long nsu);

    // Verifica se existe uma transação com um determinado código de autorização
    boolean existsByDescricaoCodigoAutorizacao(Long codigoAutorizacao);
}
