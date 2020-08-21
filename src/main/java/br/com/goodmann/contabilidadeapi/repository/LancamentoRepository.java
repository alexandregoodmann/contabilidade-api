package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.goodmann.contabilidadeapi.model.Lancamento;

public interface LancamentoRepository extends MongoRepository<Lancamento, String> {

}
