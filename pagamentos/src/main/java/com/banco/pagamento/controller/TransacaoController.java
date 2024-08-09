package com.banco.pagamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.pagamento.model.Transacao;
import com.banco.pagamento.model.TransacaoRequest;
import com.banco.pagamento.service.TransacaoService;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    // Criar uma nova transação (pagamento)
    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody TransacaoRequest request) {
        Transacao transacao = request.getTransacao();
        return transacaoService.save(transacao);
    }

    // Consultar uma transação por ID
    @GetMapping("/{id}")
    public ResponseEntity<Transacao> getTransacao(@PathVariable String id) {
        return transacaoService.getTransacao(id);
    }

    // Consultar todas as transações
    @GetMapping
    public ResponseEntity<Iterable<Transacao>> getAllTransacoes() {
        return ResponseEntity.ok(transacaoService.getAllTransacoes());
    }

    // Realizar estorno de uma transação
    @PutMapping("/estorno/{id}")
    public ResponseEntity<Transacao> estornarTransacao(@PathVariable String id) {
        return transacaoService.estornarTransacao(id);
    }
}
