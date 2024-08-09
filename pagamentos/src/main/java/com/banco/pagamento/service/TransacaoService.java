package com.banco.pagamento.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banco.pagamento.exception.TransacaoNaoEncontradaException;
import com.banco.pagamento.model.Transacao;
import com.banco.pagamento.repository.TransacaoRepository;
import com.banco.pagamento.util.Status;

@Service
public class TransacaoService {

    @Autowired
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    private Long generateUniqueValue(int length) {
        Random random = new Random();
        Long value;
        do {
            value = random.nextLong((long) Math.pow(10, length));
        } while (value < Math.pow(10, length - 1) || transacaoRepository.existsByDescricaoNsu(value) || transacaoRepository.existsByDescricaoCodigoAutorizacao(value));
        return value;
    }

    public ResponseEntity<Transacao> save(Transacao transacao) {
        try {
            // Verifica se a transação com o mesmo ID já existe
            if (transacaoRepository.existsById(transacao.getId())) {
                return ResponseEntity.status(409).body(null); // 409 Conflict
            }
            
            // Verifica se o valor na descrição é positivo
            if (transacao.getDescricao() == null || transacao.getDescricao().getValor() == null || transacao.getDescricao().getValor() <= 0) {
                return ResponseEntity.badRequest().body(null); // 400 Bad Request
            }

            // Gera nsu e codigoAutorizacao únicos
            Long nsu = generateUniqueValue(10);
            Long codigoAutorizacao = generateUniqueValue(9);

            // Define os valores gerados na transação
            if (transacao.getDescricao() != null) {
                transacao.getDescricao().setNsu(nsu);
                transacao.getDescricao().setCodigoAutorizacao(codigoAutorizacao);
                transacao.getDescricao().setStatus(Status.AUTORIZADO);
            }

            // Salva a nova transação
            Transacao savedTransacao = transacaoRepository.save(transacao);
            return ResponseEntity.ok(savedTransacao);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); 
        }
    }
    
    public ResponseEntity<Transacao> estornarTransacao(String id) {
        try {
            Optional<Transacao> optionalTransacao = transacaoRepository.findById(id);

            if (!optionalTransacao.isPresent()) {
                throw new TransacaoNaoEncontradaException("Transação com ID " + id + " não encontrada.");
            }

            Transacao transacao = optionalTransacao.get();
            if (transacao.getDescricao() != null) {
                transacao.getDescricao().setStatus(Status.NEGADO);
            }

            Transacao updatedTransacao = transacaoRepository.save(transacao);
            return ResponseEntity.ok(updatedTransacao);

        } catch (TransacaoNaoEncontradaException e) {
            return ResponseEntity.status(404).body(null); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); 
        }
    }
    
    public ResponseEntity<Transacao> getTransacao(String id){
        try {
            Optional<Transacao> transacao = transacaoRepository.findById(id);
            if (transacao.isPresent()) {
                return ResponseEntity.ok(transacao.get());
            } else {
                throw new TransacaoNaoEncontradaException("Transação com ID " + id + " não encontrada.");
            }
        } catch (TransacaoNaoEncontradaException e) {
            return ResponseEntity.status(404).body(null); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); 
        }
    }
    
    public Iterable<Transacao> getAllTransacoes() {
        try {
            return transacaoRepository.findAll();
        } catch (Exception e) {
        	throw new TransacaoNaoEncontradaException("Transação nenhuma não encontrada.");
        }
    }
}
